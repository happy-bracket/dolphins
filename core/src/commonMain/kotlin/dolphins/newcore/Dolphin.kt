package dolphins.newcore

import dolphins.core.FunDeps
import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Handle

/**
 * Main implementation of [Feature]
 * On creation, it launches a controllable loop, one iteration of which contains following steps:
 *
 * 1. Receive event of type [Ev]
 * 2. Transform it to mutation [Mu] with [Core.coHandler]
 * 3. Compute new state [St] and effects to run [Ef] by invoking [Core.update] with old state [St] and received mutation [Mu]
 * 4. Emit new state to observers
 * 5. Handle effects [Ef] with [Core.handler] and supply received events [Ev] to step 1
 * @param core - underlying core, containing stuff for initialization
 */
class Dolphin<F, St, Ev, Mu, Ef>(
    funDeps: FunDeps<F>,
    core: Core<F, St, Ev, Mu, Ef>,
    contextBoundaries: ContextBoundaries<F> = ContextBoundaries(funDeps.single())
) : Feature<F, St, Ev, Mu, Ef>, FunDeps<F> by funDeps {

    private val eventChannel = through<Ev>()
    private val stateChannel = conflated(core.initialState)

    private val flowHandle: Handle<F>

    init {
        flowHandle = eventChannel
            .suspendRead()
            .shiftTo(io())
            .concatMapEager { event -> core.coHandler.fHandle(event) }
            .flatMap { mutation ->
                pair(
                    stateChannel.suspendRead().take(1),
                    just(mutation)
                )
            }.shiftTo(contextBoundaries.update)
            .fmap { (state, mutation) ->
                core.update(state, mutation)
            }.flatMap { (state, effects) ->
                pair(
                    stateChannel.write(state),
                    merge(effects.map(core.handler::fHandle))
                )
            }.flatMap { (_, event) ->
                eventChannel.write(event)
            }.consume {}
    }

    /**
     * Method used to trigger state update
     * @return cold computation of this offer, which caller must run and handle himself
     */
    override fun offer(event: Ev): Kind<F, Unit> =
        eventChannel.write(event)

    /**
     * @return stream of states
     */
    override fun state(): Kind<F, St> =
        stateChannel.suspendRead()

    /**
     * Ends life of the feature, stopping any updates and disposing of feature lifecycle scope
     */
    fun kill() {
        flowHandle.release()
    }

}
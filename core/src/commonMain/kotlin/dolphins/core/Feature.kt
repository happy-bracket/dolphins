package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.*

/**
 * Core class, which implements basically everything.
 * On creation, it launches controllable loop of receiving events of type [Ev] through [mutate], transforming it into
 * mutations of type [Mu] (possibly with side-effects) and then emitting a new state of type [St] to all subscribers of [state].
 * The feature can be disposed of, if required, by invoking the [kill] method.
 * @param F an HKT of the underlying async library
 * @param St state type
 * @param Ev event type, which passes through coeffect handler and is mapped to [Mu]
 * @param Mu mutations type, used in update
 * @param Ef effect type
 * @param deps pack of functional dependencies (typeclasses instances)
 * @param core pack of pure stuff: initial values and an update function
 * @param coeffectHandler transformer from [Ev] to [Mu]. It may perform side-effects
 * @param effectHandler interprets effects of type [Ef], returned from update, mapping them to actual side-effects
 * and returning resulting event [Ev] back into the loop
 */
class Feature<F, St, Ev, Mu, Ef>(
    deps: FunDeps<F>,
    core: Core<St, Mu, Ef>,
    private val coeffectHandler: Handler<F, Ev, Mu>,
    private val effectHandler: Handler<F, Ef, Ev>
) : FunDeps<F> by deps {

    private val stateR = conflated(core.initialState)
    private val effectR = through<Set<Ef>>()
    private val eventR = through<Ev>()

    private val flowHandle: Handle<F>
    private val effectHandle: Handle<F>

    init {
        flowHandle = eventR
            .suspendRead()
            .shiftTo(io())
            .flatMap { event -> coeffectHandler.kindfulHandle(event) }
            .flatMap { pair(stateR.suspendRead().take(1), just(it)) }
            .shiftTo(computation())
            .fmap { (state, mutation) -> core.update(state, mutation) }
            .flatMap { (state, effects) ->
                pair(stateR.write(state), just(effects))
            }.fmap { (_, effects) -> effects }
            .flatMap { effects ->
                effectR.write(effects)
            }.consume {}

        effectHandle = effectR
            .suspendRead()
            .shiftTo(io())
            .flatMap { effects ->
                merge(effects.map(effectHandler::kindfulHandle))
            }.flatMap { m ->
                eventR.write(m)
            }.consume {}
    }

    /**
     * Method used to trigger state update
     */
    fun mutate(event: Ev): Handle<F> =
        eventR.write(event)
            .consume {}

    /**
     * @return stream of states
     */
    fun state(): Kind<F, St> =
        stateR.suspendRead()

    /**
     * Ends life of the feature, stopping any updates and disposing of feature lifecycle scope
     */
    fun kill() {
        flowHandle.release()
    }

}
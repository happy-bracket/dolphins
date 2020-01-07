package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.*

/**
 * Core class, which implements basically everything.
 * On creation, it launches controllable loop of receiving events of type [Ev] through [mutate], transforming it into
 * mutations of type [M] (possibly with side-effects) and then emitting a new state of type [S] to all subscribers of [state].
 * The feature can be disposed of, if required, by invoking the [kill] method.
 * @param G an HKT of the underlying async library
 * @param S state type
 * @param Ev event type, which passes through coeffect handler and is mapped to [M]
 * @param M mutations type, used in update
 * @param E effect type
 * @param deps pack of functional dependencies (typeclasses instances)
 * @param core pack of pure stuff: initial values and an update function
 * @param coeffectHandler transformer from [Ev] to [M]. It may perform side-effects
 * @param effectHandler interprets effects of type [E], returned from update, mapping them to actual side-effects
 * and returning resulting event [Ev] back into the loop
 */
class Feature<G, S, Ev, M, E>(
    deps: FunDeps<G>,
    core: Core<S, M, E>,
    private val coeffectHandler: Handler<G, Ev, M>,
    private val effectHandler: Handler<G, E, Ev>
) : FunDeps<G> by deps {

    private val stateR = conflated(core.initialState)
    private val effectR = through<Set<E>>()
    private val eventR = through<Ev>()

    private val flowHandle: Handle<G>
    private val effectHandle: Handle<G>

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
    fun mutate(event: Ev): Handle<G> =
        eventR.write(event)
            .consume {}

    /**
     * @return stream of states
     */
    fun state(): Kind<G, S> =
        stateR.suspendRead()

    /**
     * Ends life of the feature, stopping any updates and disposing of feature lifecycle scope
     */
    fun kill() {
        flowHandle.release()
    }

}
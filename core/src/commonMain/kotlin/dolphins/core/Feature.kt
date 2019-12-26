package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.*

/**
 * Core class, which implements basically everything.
 * @param G - stream type
 * @param S - state type
 * @param V - state view type, to which state will be mapped
 * @param Ev - event type, which may obtain coeffects and be mapped to [M]
 * @param M - mutations type
 * @param E - effect type
 */
class Feature<G, S, V, Ev, M, E>(
    deps: FunDeps<G>,
    core: Core<S, M, E>,
    private val cofx: Handler<G, Ev, M>,
    private val handler: Handler<G, E, Ev>
) : FunDeps<G> by deps {

    private val stateR = conflated<S>()
    private val eventR = through<Ev>()
    private val flowHandle: Handle<G>

    init {
        flowHandle = eventR
            .suspendRead()
            .shiftTo(io())
            .flatMap { event -> cofx.handle(event) }
            .shiftTo(computation())
            .scan(core.initialState to core.initialEffects) { (state, _), m ->
                core.update(state, m)
            }.flatMap { (state, effects) ->
                pair(stateR.write(state), just(effects))
            }.fmap { (_, effects) -> effects }
            .shiftTo(io())
            .flatMap { effs ->
                merge(effs.map(handler::handle))
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
     * @return stream of views
     */
    fun select(view: (S) -> V): Kind<G, V> =
        stateR.suspendRead()
            .fmap(view)

    fun kill() {
        flowHandle.release()
    }

}
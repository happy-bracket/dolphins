package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.*

/**
 * Core class, which implements basically everything.
 * @param G - stream type
 * @param D - type of dispatcher entity associated with [G]
 * @param H - type of subscription handle, associated with [G]
 * @param S - state type
 * @param M - mutations type
 * @param E - effect type
 */
class Feature<G, D, H : Handle, S, Ev, M, E>(
    initialState: S,
    initialEffects: Set<E>,
    private val flow: FeatureFlow<G, Ev, M, S, E>,
    deps: FunDeps<G, D, H>
) : FunDeps<G, D, H> by deps {

    private val stateR = conflated<S>()
    private val eventR = through<Ev>()
    private val flowHandle: H // TODO: implement scoping

    init {
        flowHandle = eventR
            .suspendRead()
            .flatMap { flow.pre.cofx(it) }
            .shiftTo(computation())
            .scan(initialState to initialEffects) { (state, _), m ->
                flow.update(state, m)
            }.flatMap { (state, effects) ->
                pair(stateR.write(state), just(effects))
            }.fmap { (_, effects) -> effects }
            .consume { effects ->
                effects.map(flow.handler::handle)
                    .forEach { future ->
                        future.flatMap {
                            eventR.write(it)
                        }.consume {} // TODO: implement scoping
                    }
            }
    }

    /**
     * Method used to trigger state update
     */
    fun accept(event: Ev) {
        eventR.write(event)
            .consume {} // TODO: implement scoping
    }

    /**
     * @return stream of states
     */
    fun state(): Kind<G, S> =
        stateR.suspendRead()

}
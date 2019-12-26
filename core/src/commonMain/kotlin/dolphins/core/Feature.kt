package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.*

/**
 * Core class, which implements basically everything.
 * @param G - stream type
 * @param S - state type
 * @param M - mutations type
 * @param E - effect type
 */
class Feature<G, S, M, E>(
    initialState: S,
    initialEffects: Set<E>,
    private val update: (S, M) -> Pair<S, Set<E>>,
    private val handler: Handler<G, E, M>,
    deps: FunDeps<G>
) : FunDeps<G> by deps {

    private val stateR = conflated<S>()
    private val mutationR = through<M>()
    private val flowHandle: Handle<G> // TODO: implement scoping

    init {
        flowHandle = mutationR
            .suspendRead()
            .shiftTo(computation())
            .scan(initialState to initialEffects) { (state, _), m ->
                update(state, m)
            }.flatMap { (state, effects) ->
                pair(stateR.write(state), just(effects))
            }.fmap { (_, effects) -> effects }
            .consume { effects ->
                effects.map(handler::handle)
                    .forEach { future ->
                        future.flatMap {
                            mutationR.write(it)
                        }.consume {} // TODO: implement scoping
                    }
            }
    }

    /**
     * Method used to trigger state update
     */
    fun mutate(mutation: M) {
        mutationR.write(mutation)
            .consume {} // TODO: implement scoping
    }

    /**
     * @return stream of states
     */
    fun state(): Kind<G, S> =
        stateR.suspendRead()

}

interface Handler<F, in E, out M> : Monad<F> {

    fun handle(e: E): Kind<F, M>

}
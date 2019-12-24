package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.*
import dolphins.foundation.types.either.Either
import dolphins.foundation.types.either.left
import dolphins.foundation.types.either.right

/**
 * Core class, which implements basically everything.
 * @param G - stream type
 * @param D - type of dispatcher entity associated with [G]
 * @param H - type of subscription handle, associated with [G]
 * @param S - state type
 * @param M - mutations type
 * @param E - effect type
 */
class Feature<G, D, H : Handle, S, M, E>(
    initialState: S,
    initialEffects: Set<E>,
    private val update: (S, M) -> Pair<S, Set<E>>,
    private val handler: Handler<G, E, M>,
    private val channel: Channel<G>,
    private val shift: Shift<G, D>,
    private val consume: Consume<G, H>,
    private val stream: Stream<G>
) : Channel<G> by channel, Stream<G> by stream, Shift<G, D> by shift, Consume<G, H> by consume {

    private val stateR = conflated<S>()
    private val mutationR = through<M>()
    private val flowHandle: H // TODO: implement scoping

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
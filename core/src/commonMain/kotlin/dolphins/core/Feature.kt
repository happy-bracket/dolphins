package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Consume
import dolphins.foundation.typeclasses.Handle
import dolphins.foundation.typeclasses.Rendezvous
import dolphins.foundation.typeclasses.Shift
import dolphins.foundation.types.either.Either
import dolphins.foundation.types.either.left
import dolphins.foundation.types.either.right

class Feature<G, R, D, H : Handle, S, M, E, A>(
    initialState: S,
    initialEffects: Set<E>,
    private val update: (S, M) -> Pair<S, Set<E>>,
    private val handler: Handler<E, M, A>,
    private val rendezvous: Rendezvous<G, R>,
    private val shift: Shift<G, D>,
    private val consume: Consume<G, H>
) : Rendezvous<G, R> by rendezvous, Shift<G, D> by shift, Consume<G, H> by consume {

    private val stateR = conflated<S>()
    private val actionR = through<A>()
    private val mutationR = through<M>()
    private val flowHandle: H

    init {
        flowHandle = mutationR
            .publish()
            .shiftTo(computation())
            .scan(initialState to initialEffects) { (state, _), m ->
                update(state, m)
            }.consume { (state, effects) ->
                stateR.push(state)
                effects.forEach { effect ->
                    handler.handle(effect, ::mutate) { actionR.push(it) }
                }
            }
    }

    fun mutate(mutation: M) {
        mutationR.push(mutation)
    }

    fun updates(): Kind<G, Either<S, A>> =
        merge(
            state().fmap { state -> state.left() },
            actions().fmap { action -> action.right() }
        )

    fun state(): Kind<G, S> =
        stateR.publish()

    fun actions(): Kind<G, A> =
        actionR.publish()

}

interface Handler<in E, out M, out A> {

    fun handle(e: E, msink: (M) -> Unit, asink: (A) -> Unit)

}
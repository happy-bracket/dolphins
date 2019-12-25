package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Monad

typealias Update<S, M, E> = (S, M) -> Pair<S, Set<E>>

class FeatureFlow<F, Ev, M, S, E>(
    val pre: PreHandler<F, Ev, M>,
    val update: Update<S, M, E>,
    val handler: Handler<F, E, Ev>
)

typealias MonoFlow<F, M, S, E> = FeatureFlow<F, M, M, S, E>

fun <F, M, S, E> MonoFlow(
    update: (S, M) -> Pair<S, Set<E>>,
    handler: Handler<F, E, M>,
    monad: Monad<F>
): MonoFlow<F, M, S, E> =
    FeatureFlow(
        IdlePreHandler(monad),
        update,
        handler
    )

interface Handler<F, in E, out M> : Monad<F> {

    fun handle(e: E): Kind<F, M>

}

interface PreHandler<F, Ev, M> : Monad<F> {

    fun cofx(mutation: Ev): Kind<F, M>

}

class IdlePreHandler<F, M>(private val monad: Monad<F>) : PreHandler<F, M, M>, Monad<F> by monad {
    override fun cofx(mutation: M): Kind<F, M> = just(mutation)
}
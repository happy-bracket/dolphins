package dolphins.foundation.typeclasses

import dolphins.foundation.Kind
import dolphins.foundation.ShadyException

interface Applicative<F> : Functor<F> {

    fun <A, B, C> lift2(f: (A, B) -> C): (Kind<F, A>, Kind<F, B>) -> Kind<F, C>

    fun <A, B, C> zip(a: Kind<F, A>, b: Kind<F, B>, f: (A, B) -> C): Kind<F, C> =
        lift2(f)(a, b)

    fun <A, B> pair(a: Kind<F, A>, b: Kind<F, B>): Kind<F, Pair<A, B>> =
        zip(a, b, ::Pair)

}

interface ShadyApplicative<F> : Applicative<F> {

    override fun <A, B, C> lift2(f: (A, B) -> C): (Kind<F, A>, Kind<F, B>) -> Kind<F, C> =
        throw ShadyException("Applicative")

    override fun <A, B, C> zip(a: Kind<F, A>, b: Kind<F, B>, f: (A, B) -> C): Kind<F, C>

}
package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

interface Monad<F> : Applicative<F> {

    fun <A> just(value: A): Kind<F, A>

    fun <A, B> Kind<F, A>.flatMap(f: (A) -> Kind<F, B>): Kind<F, B>

}
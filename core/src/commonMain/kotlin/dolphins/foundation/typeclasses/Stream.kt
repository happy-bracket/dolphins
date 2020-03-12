package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

/**
 * Identifies that [F], being a monad, can produce more than one value and thus different instances of `F<_>`
 * can be composed accordingly.
 */
interface Stream<F> : Monad<F> {

    fun <A, B> Kind<F, A>.scan(acc: B, f: (B, A) -> B): Kind<F, B>

    fun <A> merge(
        vararg ss: Kind<F, A>
    ) : Kind<F, A>

    fun <A> merge(
        ss: List<Kind<F, A>>
    ): Kind<F, A>

    fun <A, B> Kind<F, A>.concatMapEager(f: (A) -> Kind<F, B>): Kind<F, B>

    fun <A> Kind<F, A>.take(number: Int): Kind<F, A>

}
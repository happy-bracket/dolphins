package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

/**
 * Represents pull/push computed stream of values.
 */
interface Stream<F> : Monad<F> {

    fun <A, B> Kind<F, A>.scan(acc: B, f: (B, A) -> B): Kind<F, B>

    fun <A> merge(
        vararg ss: Kind<F, A>
    ) : Kind<F, A>

    fun <A> merge(
        ss: List<Kind<F, A>>
    ): Kind<F, A>

}
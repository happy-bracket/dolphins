package ru.hbracket.dolphins.f.ts

import ru.hbracket.dolphins.f.Kind

/**
 * Represents pull/push computed stream of values.
 */
interface Stream<F> : Monad<F> {

    fun <A, B> Kind<F, A>.scan(acc: B, f: (B, A) -> B): Kind<F, B>

    fun <A> merge(
        vararg ss: Kind<F, A>
    ) : Kind<F, A>

}
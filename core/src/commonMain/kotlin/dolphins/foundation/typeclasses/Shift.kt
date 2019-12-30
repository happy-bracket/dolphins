package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

/**
 * For concurrent primitives, provides ways to change execution context.
 */
interface Shift<F> {

    fun io(): ExecContext<F>
    fun computation(): ExecContext<F>
    fun <A> Kind<F, A>.shiftTo(execContext: ExecContext<F>): Kind<F, A>

}

interface UiShift<F> : Shift<F> {

    fun main(): ExecContext<F>

}

interface ExecContext<F>
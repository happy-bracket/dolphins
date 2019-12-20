package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

/**
 * For concurrent primitives, provides ways to change execution context.
 * [D] is the type of context. For RxJava it will be `io.reactivex.Scheduler`, for coroutines - Dispatcher
 */
interface Shift<F, D> {

    fun io(): D
    fun computation(): D
    fun <A> Kind<F, A>.shiftTo(dispatcher: D): Kind<F, A>

}

interface UiShift<F, D> : Shift<F, D> {

    fun main(): D

}
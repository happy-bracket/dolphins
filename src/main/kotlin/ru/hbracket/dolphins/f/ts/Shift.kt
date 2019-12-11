package ru.hbracket.dolphins.f.ts

import ru.hbracket.dolphins.f.Kind

/**
 * For concurrent primitives, provides ways to change execution context.
 * [D] is the type of context. For RxJava it will be `io.reactivex.Dispatcher`, for coroutines - TODO
 */
interface Shift<F, D> {

    fun io(): D
    fun computation(): D
    fun <A> Kind<F, A>.shiftTo(dispatcher: D): Kind<F, A>

}

interface UiShift<F, D> : Shift<F, D> {

    fun main(): D

}
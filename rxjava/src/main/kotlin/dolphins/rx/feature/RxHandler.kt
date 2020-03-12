package dolphins.rx.feature

import dolphins.foundation.Kind
import dolphins.newcore.*
import dolphins.rx.instances.observable.Monad
import dolphins.rx.instances.observable.Stream
import dolphins.rx.types.Rx
import dolphins.rx.types.unfix
import io.reactivex.Observable

/**
 * Override this to create effect and coeffect handlers.
 */
abstract class RxHandler<In, Out> : Handler<Rx, In, Out> {

    final override fun fHandle(input: In): Kind<Rx, Out> = handle(input).unfix()

    abstract fun handle(input: In): Observable<Out>

    companion object

}

fun <A> RxHandler.Companion.Identity(): Handler<Rx, A, A> =
    Handler.Identity(Rx.Monad)

fun <A, B> RxHandler.Companion.FromFunction(perform: (A) -> Observable<B>): Handler<Rx, A, B> =
    object : RxHandler<A, B>() {

        override fun handle(input: A): Observable<B> {
            return perform(input)
        }

    }

fun RxHandler.Companion.Idle(): Handler<Rx, Any, Nothing> =
    Handler.Idle(Rx.Stream)

infix fun <A, B, C> RxHandler<A, B>.then(handler: RxHandler<B, C>): Handler<Rx, A, C> =
    then(Rx.Monad, handler)

infix fun <A, B, C> RxHandler<B, C>.dot(handler: RxHandler<A, B>): Handler<Rx, A, C> =
    dot(Rx.Monad, handler)
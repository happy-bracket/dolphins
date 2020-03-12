package dolphins.newcore

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Monad
import dolphins.foundation.typeclasses.Stream

fun <F, A> Handler.Companion.Identity(M: Monad<F>): Handler<F, A, A> =
    object : Handler<F, A, A> {

        override fun fHandle(input: A): Kind<F, A> {
            return M.just(input)
        }

    }

fun <F, A, B> Handler.Companion.FromFunction(perform: (A) -> Kind<F, B>): Handler<F, A, B> =
    object : Handler<F, A, B> {

        override fun fHandle(input: A): Kind<F, B> {
            return perform(input)
        }

    }

fun <F> Handler.Companion.Idle(S: Stream<F>): Handler<F, Any, Nothing> =
    object : Handler<F, Any, Nothing> {

        override fun fHandle(input: Any): Kind<F, Nothing> {
            return S.empty()
        }

    }

fun <F, A, B, C> Handler<F, A, B>.then(M: Monad<F>, handler: Handler<F, B, C>): Handler<F, A, C> =
    object : Handler<F, A, C> {

        override fun fHandle(input: A): Kind<F, C> {
            return M.run {
                this@then.fHandle(input).flatMap(handler::fHandle)
            }
        }

    }

fun <F, A, B, C> Handler<F, B, C>.dot(M: Monad<F>, handler: Handler<F, A, B>): Handler<F, A, C> =
    handler.then(M, this)
package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

interface Rendezvous<F, R> : Stream<F> {

    fun <A> through(): Kind<R, A>
    fun <A> conflated(): Kind<R, A>

    fun <A> Kind<R, A>.push(value: A)
    fun <A> Kind<R, A>.publish(): Kind<F, A>

}
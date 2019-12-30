package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

/**
 * Describes imperative consumption of a delayed computation, with a handle of type [H] to cancel it
 */
interface Consume<F> {

    fun <A> Kind<F, A>.consume(f: (A) -> Unit): Handle<F>

}

interface Handle<F> {

    fun release()

}
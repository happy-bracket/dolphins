package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

interface Consume<F, H : Handle> {

    fun <A> Kind<F, A>.consume(f: (A) -> Unit): H

}

interface Handle {

    fun release()

}
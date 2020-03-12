package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Consume
import dolphins.rx.types.Rx
import dolphins.rx.types.RxVal
import dolphins.rx.types.RxHandle
import dolphins.rx.types.fix

private val RxConsumeInstance: Consume<Rx> =
    object : Consume<Rx> {

        override fun <A> Kind<Rx, A>.consume(f: (A) -> Unit): RxHandle =
            RxHandle(fix().subscribe(f))

    }

val Rx.Companion.Consume
    get() = RxConsumeInstance
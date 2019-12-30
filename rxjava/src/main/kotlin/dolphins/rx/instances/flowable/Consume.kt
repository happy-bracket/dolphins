package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Consume
import dolphins.rx.types.ForRx
import dolphins.rx.types.Rx
import dolphins.rx.types.RxHandle
import dolphins.rx.types.fix

private val RxConsumeInstance: Consume<ForRx> =
    object : Consume<ForRx> {

        override fun <A> Kind<ForRx, A>.consume(f: (A) -> Unit): RxHandle =
            RxHandle(fix().subscribe(f))

    }

val Rx.Companion.Consume
    get() = RxConsumeInstance
package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Consume
import dolphins.rx.instances.RxHandle

val FlowableConsume: Consume<ForFlowable, RxHandle> =
    object : Consume<ForFlowable, RxHandle> {

        override fun <A> Kind<ForFlowable, A>.consume(f: (A) -> Unit): RxHandle =
            RxHandle(fix().subscribe(f))

    }
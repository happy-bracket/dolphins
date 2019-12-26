package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Consume
import dolphins.foundation.typeclasses.Handle
import io.reactivex.disposables.Disposable

val FlowableConsume: Consume<ForFlowable> =
    object : Consume<ForFlowable> {

        override fun <A> Kind<ForFlowable, A>.consume(f: (A) -> Unit): RxHandle =
            RxHandle(fix().subscribe(f))

    }

class RxHandle(val disposable: Disposable) : Handle<ForFlowable> {
    override fun release() {
        disposable.dispose()
    }
}


package dolphins.rx.types

import dolphins.foundation.typeclasses.Handle
import io.reactivex.disposables.Disposable

class RxHandle(val disposable: Disposable) : Handle<Rx> {
    override fun release() {
        disposable.dispose()
    }
}

fun Handle<Rx>.fix() = (this as RxHandle).disposable
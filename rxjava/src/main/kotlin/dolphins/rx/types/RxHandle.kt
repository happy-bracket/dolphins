package dolphins.rx.types

import dolphins.foundation.typeclasses.Handle
import io.reactivex.disposables.Disposable

class RxHandle(val disposable: Disposable) : Handle<ForRx> {
    override fun release() {
        disposable.dispose()
    }
}

fun Handle<ForRx>.fix() = (this as RxHandle).disposable
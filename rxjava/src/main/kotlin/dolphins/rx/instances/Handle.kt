package dolphins.rx.instances

import dolphins.foundation.typeclasses.Handle
import io.reactivex.disposables.Disposable

data class RxHandle(private val disposable: Disposable) : Handle {

    override fun release() {
        disposable.dispose()
    }

}
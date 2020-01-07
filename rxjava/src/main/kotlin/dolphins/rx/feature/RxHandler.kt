package dolphins.rx.feature

import dolphins.core.FunDeps
import dolphins.core.Handler
import dolphins.core.postMap
import dolphins.core.preMap
import dolphins.foundation.Kind
import dolphins.rx.types.ForRx
import dolphins.rx.types.unfix
import io.reactivex.Observable

/**
 * Override this to create effect and coeffect handlers.
 */
abstract class RxHandler<E, M> : Handler<ForRx, E, M>, FunDeps<ForRx> by RxDepBag {

    override fun kindfulHandle(e: E): Kind<ForRx, M> = handle(e).unfix()

    abstract fun handle(e: E): Observable<M>

}

/**
 * Creates handler out of air
 */
fun <E, M> rxHandler(handle: (E) -> Observable<M>): RxHandler<E, M> =
    object : RxHandler<E, M>() {
        override fun handle(e: E): Observable<M> = handle(e)
    }
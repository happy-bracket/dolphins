package dolphins.rx.feature

import dolphins.rx.instances.flowable.fix
import io.reactivex.Observable

fun <S> RxFeature<S, *, *>.rxState(): Observable<S> =
    state().fix()
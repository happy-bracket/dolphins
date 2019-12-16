package dolphins.rx.feature

import dolphins.foundation.types.either.Either
import dolphins.rx.instances.flowable.fix
import io.reactivex.Observable

fun <S> RxFeature<S, *, *, *>.rxState(): Observable<S> =
    state().fix()

fun <A> RxFeature<*, *, *, A>.rxActions(): Observable<A> =
    actions().fix()

fun <S, A> RxFeature<S, *, *, A>.rxUpdates(): Observable<Either<S, A>> =
    updates().fix()
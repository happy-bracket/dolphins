package dolphins.rx.feature

import dolphins.rx.instances.flowable.fix
import io.reactivex.disposables.Disposable

fun <Ev> RxFeature<*, Ev, *, *>.rxMutate(event: Ev): Disposable =
    mutate(event).fix()

fun <S> RxFeature<S, *, *, *>.rxState() =
    state().fix()
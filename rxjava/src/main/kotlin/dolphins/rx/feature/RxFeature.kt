package dolphins.rx.feature

import dolphins.core.Feature
import dolphins.core.Handler
import dolphins.rx.instances.RxHandle
import dolphins.rx.instances.flowable.*
import dolphins.rx.instances.subject.ForSubject
import io.reactivex.Scheduler

typealias RxFeature<S, M, E> =
        Feature<ForFlowable,
                Scheduler,
                RxHandle,
                S, M, E>

fun <S, M, E> RxFeature(
    initialState: S,
    initialEffects: Set<E>,
    update: (S, M) -> Pair<S, Set<E>>,
    handler: Handler<ForFlowable, E, M>
): RxFeature<S, M, E> =
    Feature(
        initialState = initialState,
        initialEffects = initialEffects,
        update = update,
        handler = handler,
        deps = RxDepsBag
    )
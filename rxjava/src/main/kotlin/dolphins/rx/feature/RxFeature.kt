package dolphins.rx.feature

import dolphins.core.Feature
import dolphins.core.Handler
import dolphins.rx.instances.RxHandle
import dolphins.rx.instances.flowable.FlowableConsume
import dolphins.rx.instances.flowable.FlowableRendezvous
import dolphins.rx.instances.flowable.FlowableShift
import dolphins.rx.instances.flowable.ForFlowable
import dolphins.rx.instances.subject.ForSubject
import io.reactivex.Scheduler

typealias RxFeature<S, M, E, A> =
        Feature<
                ForFlowable,
                ForSubject,
                Scheduler,
                RxHandle,
                S, M, E, A>

fun <S, M, E, A> RxFeature(
    initialState: S,
    initialEffects: Set<E>,
    update: (S, M) -> Pair<S, Set<E>>,
    handler: Handler<E, M, A>
): RxFeature<S, M, E, A> =
    Feature(
        initialState,
        initialEffects,
        update,
        handler,
        FlowableRendezvous,
        FlowableShift,
        FlowableConsume
    )
package dolphins.rx.feature

import dolphins.core.Feature
import dolphins.rx.instances.flowable.ForFlowable

typealias RxFeature<S, Ev, M, E> = Feature<ForFlowable, S, Ev, M, E>
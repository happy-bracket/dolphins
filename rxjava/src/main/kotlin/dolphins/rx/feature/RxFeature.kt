package dolphins.rx.feature

import dolphins.core.Feature
import dolphins.rx.types.ForRx

typealias RxFeature<S, Ev, M, E> = Feature<ForRx, S, Ev, M, E>
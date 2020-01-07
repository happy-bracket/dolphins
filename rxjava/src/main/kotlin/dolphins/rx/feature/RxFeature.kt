package dolphins.rx.feature

import dolphins.core.Core
import dolphins.core.Feature
import dolphins.core.Handler
import dolphins.core.IdentityCofx
import dolphins.rx.types.ForRx

typealias RxFeature<S, Ev, M, E> = Feature<ForRx, S, Ev, M, E>
typealias RxMonoFeature<S, M, E> = RxFeature<S, M, M, E>

fun <S, Ev, M, E> RxFeature(
    core: Core<S, M, E>,
    coeffects: Handler<ForRx, Ev, M>,
    effects: Handler<ForRx, E, Ev>
): RxFeature<S, Ev, M, E> =
    Feature(
        RxDepBag,
        core,
        coeffects,
        effects
    )

fun <S, M, E> RxMonoFeature(
    core: Core<S, M, E>,
    effects: RxHandler<E, M>
): RxMonoFeature<S, M, E> =
    RxFeature(
        core,
        IdentityCofx(RxDepBag),
        effects
    )
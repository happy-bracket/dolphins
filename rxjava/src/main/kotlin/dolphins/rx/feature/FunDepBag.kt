package dolphins.rx.feature

import dolphins.core.FunDepsBag
import dolphins.rx.instances.flowable.Channel
import dolphins.rx.instances.flowable.Consume
import dolphins.rx.instances.flowable.Shift
import dolphins.rx.instances.flowable.Stream
import dolphins.rx.types.Rx

val RxDepBag =
    FunDepsBag(
        Rx.Channel,
        Rx.Shift,
        Rx.Consume,
        Rx.Stream
    )
package dolphins.rx.feature

import dolphins.core.FunDepsBag
import dolphins.rx.instances.observable.Channel
import dolphins.rx.instances.observable.Consume
import dolphins.rx.instances.observable.Shift
import dolphins.rx.instances.observable.Stream
import dolphins.rx.types.Rx

val RxDepBag =
    FunDepsBag(
        Rx.Channel,
        Rx.Shift,
        Rx.Consume,
        Rx.Stream
    )
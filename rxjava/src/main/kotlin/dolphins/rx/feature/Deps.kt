package dolphins.rx.feature

import dolphins.core.FunDepsBag
import dolphins.rx.instances.flowable.FlowableChannel
import dolphins.rx.instances.flowable.FlowableConsume
import dolphins.rx.instances.flowable.FlowableShift
import dolphins.rx.instances.flowable.FlowableStream

val RxDepsBag =
    FunDepsBag(
        channel = FlowableChannel,
        shift = FlowableShift,
        consume = FlowableConsume,
        stream = FlowableStream
    )
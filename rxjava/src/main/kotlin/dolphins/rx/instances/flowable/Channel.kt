package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Channel
import dolphins.foundation.types.channel.ChannelVal
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

val FlowableChannel =
    object : Channel<ForFlowable> {

        override fun <A> conflated(): ChannelVal<ForFlowable, A> =
            RxChannel(BehaviorSubject.create())

        override fun <A> through(): ChannelVal<ForFlowable, A> =
            RxChannel(PublishSubject.create())

        override fun <A> ChannelVal<ForFlowable, A>.write(value: A): Kind<ForFlowable, Unit> =
            Observable.fromCallable {
                fix().subject.onNext(value)
            }.unfix()

        override fun <A> ChannelVal<ForFlowable, A>.suspendRead(): Kind<ForFlowable, A> =
            fix().subject.unfix()

    }

class RxChannel<A>(val subject: Subject<A>) : ChannelVal<ForFlowable, A>

fun <A> ChannelVal<ForFlowable, A>.fix(): RxChannel<A> = this as RxChannel
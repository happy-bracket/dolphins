package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Channel
import dolphins.foundation.types.channel.ChannelVal
import dolphins.rx.types.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

private val RxChannelInstance =
    object : Channel<ForRx> {

        override fun <A> conflated(): ChannelVal<ForRx, A> =
            RxChannel(BehaviorSubject.create())

        override fun <A> conflated(default: A): ChannelVal<ForRx, A> =
            RxChannel(BehaviorSubject.createDefault(default))

        override fun <A> through(): ChannelVal<ForRx, A> =
            RxChannel(PublishSubject.create())

        override fun <A> ChannelVal<ForRx, A>.write(value: A): Kind<ForRx, Unit> =
            Observable.fromCallable {
                fix().subject.onNext(value)
            }.unfix()

        override fun <A> ChannelVal<ForRx, A>.suspendRead(): Kind<ForRx, A> =
            fix().subject.unfix()

    }

val Rx.Companion.Channel
    get() = RxChannelInstance
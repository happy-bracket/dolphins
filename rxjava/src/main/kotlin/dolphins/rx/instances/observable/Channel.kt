package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Channel
import dolphins.foundation.types.channel.ChannelVal
import dolphins.rx.types.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

private val RxChannelInstance =
    object : Channel<Rx> {

        override fun <A> conflated(): ChannelVal<Rx, A> =
            RxChannel(BehaviorSubject.create())

        override fun <A> conflated(default: A): ChannelVal<Rx, A> =
            RxChannel(BehaviorSubject.createDefault(default))

        override fun <A> through(): ChannelVal<Rx, A> =
            RxChannel(PublishSubject.create())

        override fun <A> ChannelVal<Rx, A>.write(value: A): Kind<Rx, Unit> =
            Observable.fromCallable {
                fix().subject.onNext(value)
            }.unfix()

        override fun <A> ChannelVal<Rx, A>.suspendRead(): Kind<Rx, A> =
            fix().subject.unfix()

    }

val Rx.Companion.Channel
    get() = RxChannelInstance
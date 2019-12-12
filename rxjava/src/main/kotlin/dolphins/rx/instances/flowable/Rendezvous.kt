package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Rendezvous
import dolphins.foundation.typeclasses.Stream
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import dolphins.rx.instances.subject.FSubject
import dolphins.rx.instances.subject.ForSubject
import dolphins.rx.instances.subject.fix

val FlowableRendezvous: Rendezvous<ForFlowable, ForSubject> =
    object : Rendezvous<ForFlowable, ForSubject>, Stream<ForFlowable> by FlowableStream {

        override fun <A> through(): Kind<ForSubject, A> =
            FSubject(PublishSubject.create())

        override fun <A> conflated(): Kind<ForSubject, A> =
            FSubject(BehaviorSubject.create())

        override fun <A> Kind<ForSubject, A>.publish(): Kind<ForFlowable, A> =
            fix().hide().unfix()

        override fun <A> Kind<ForSubject, A>.push(value: A) =
            fix().onNext(value)

    }
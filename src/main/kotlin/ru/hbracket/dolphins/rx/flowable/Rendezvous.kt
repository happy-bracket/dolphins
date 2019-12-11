package ru.hbracket.dolphins.rx.flowable

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ts.Rendezvous
import ru.hbracket.dolphins.f.ts.Stream
import ru.hbracket.dolphins.rx.subject.FSubject
import ru.hbracket.dolphins.rx.subject.ForSubject
import ru.hbracket.dolphins.rx.subject.fix

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
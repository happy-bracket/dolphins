package ru.hbracket.dolphins.rx.flowable

import io.reactivex.rxjava3.core.Observable
import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ts.Monad
import ru.hbracket.dolphins.f.ts.Stream

val FlowableStream: Stream<ForFlowable> =
    object : Stream<ForFlowable>, Monad<ForFlowable> by FlowableMonad {

        override fun <A, B> Kind<ForFlowable, A>.scan(acc: B, f: (B, A) -> B): Kind<ForFlowable, B> =
            fix().scan(acc, f).unfix()

        override fun <A> merge(vararg ss: Kind<ForFlowable, A>): Kind<ForFlowable, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

    }
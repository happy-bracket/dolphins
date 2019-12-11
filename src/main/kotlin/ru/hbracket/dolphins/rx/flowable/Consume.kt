package ru.hbracket.dolphins.rx.flowable

import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ts.Consume
import ru.hbracket.dolphins.rx.RxHandle

val FlowableConsume: Consume<ForFlowable, RxHandle> =
    object : Consume<ForFlowable, RxHandle> {

        override fun <A> Kind<ForFlowable, A>.consume(f: (A) -> Unit): RxHandle =
            RxHandle(fix().subscribe(f))

    }
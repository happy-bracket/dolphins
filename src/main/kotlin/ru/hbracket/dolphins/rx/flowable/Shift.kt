package ru.hbracket.dolphins.rx.flowable

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ts.Shift

val FlowableShift: Shift<ForFlowable, Scheduler> =
    object : Shift<ForFlowable, Scheduler> {

        override fun computation(): Scheduler =
            Schedulers.computation()

        override fun io(): Scheduler =
            Schedulers.io()

        override fun <A> Kind<ForFlowable, A>.shiftTo(dispatcher: Scheduler): Kind<ForFlowable, A> =
            fix().observeOn(dispatcher).unfix()

    }
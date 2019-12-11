package ru.hbracket.dolphins

import io.reactivex.rxjava3.core.Scheduler
import ru.hbracket.dolphins.rx.RxHandle
import ru.hbracket.dolphins.rx.flowable.FlowableConsume
import ru.hbracket.dolphins.rx.flowable.FlowableRendezvous
import ru.hbracket.dolphins.rx.flowable.FlowableShift
import ru.hbracket.dolphins.rx.flowable.ForFlowable
import ru.hbracket.dolphins.rx.subject.ForSubject
import ru.hbracket.dolphins.tea.Feature
import ru.hbracket.dolphins.tea.Handler

object IdleHandler : Handler<Nothing, Nothing, Nothing> {

    override fun handle(e: Nothing, msink: (Nothing) -> Unit, asink: (Nothing) -> Unit) {
    }

}

fun sample() {

    val a: (String, String) -> Pair<String, Set<Nothing>> = { s, m ->
        s + m to emptySet()
    }

    val feature: Feature<
            ForFlowable,
            ForSubject,
            Scheduler,
            RxHandle,
            String,
            String,
            Nothing,
            Nothing> = Feature(
        "",
        emptySet(),
        a,
        IdleHandler,
        FlowableRendezvous,
        FlowableShift,
        FlowableConsume
    )

}
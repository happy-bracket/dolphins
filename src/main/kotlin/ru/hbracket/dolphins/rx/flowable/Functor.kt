package ru.hbracket.dolphins.rx.flowable

import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ts.Functor
import ru.hbracket.dolphins.f.ts.ShadyFunctor

val FlowableFunctor : Functor<ForFlowable> =
    object : ShadyFunctor<ForFlowable> {

        override fun <A, B> Kind<ForFlowable, A>.fmap(f: (A) -> B): Kind<ForFlowable, B> =
            fix().map(f).unfix()

    }
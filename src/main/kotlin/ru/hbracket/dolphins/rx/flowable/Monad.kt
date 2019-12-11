package ru.hbracket.dolphins.rx.flowable

import io.reactivex.rxjava3.core.Observable
import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ts.Functor
import ru.hbracket.dolphins.f.ts.Monad

val FlowableMonad: Monad<ForFlowable> =
    object : Monad<ForFlowable>, Functor<ForFlowable> by FlowableFunctor {

        override fun <A> just(value: A): Kind<ForFlowable, A> =
            Observable.just(value).unfix()

        override fun <A, B> Kind<ForFlowable, A>.flatMap(f: (A) -> Kind<ForFlowable, B>): Kind<ForFlowable, B> =
            fix().flatMap { f(it).fix() }.unfix()

    }
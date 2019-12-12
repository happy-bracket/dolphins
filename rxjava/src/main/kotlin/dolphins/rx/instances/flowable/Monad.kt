package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.Monad
import io.reactivex.Observable

val FlowableMonad: Monad<ForFlowable> =
    object : Monad<ForFlowable>, Functor<ForFlowable> by FlowableFunctor {

        override fun <A> just(value: A): Kind<ForFlowable, A> =
            Observable.just(value).unfix()

        override fun <A, B> Kind<ForFlowable, A>.flatMap(f: (A) -> Kind<ForFlowable, B>): Kind<ForFlowable, B> =
            fix().flatMap { f(it).fix() }.unfix()

    }
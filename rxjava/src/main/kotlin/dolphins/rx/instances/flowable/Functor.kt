package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.ShadyFunctor

val FlowableFunctor : Functor<ForFlowable> =
    object : ShadyFunctor<ForFlowable> {

        override fun <A, B> Kind<ForFlowable, A>.fmap(f: (A) -> B): Kind<ForFlowable, B> =
            fix().map(f).unfix()

    }
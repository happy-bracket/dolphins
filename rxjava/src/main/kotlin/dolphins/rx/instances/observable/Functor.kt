package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.ShadyFunctor
import dolphins.rx.types.Rx
import dolphins.rx.types.RxVal
import dolphins.rx.types.fix
import dolphins.rx.types.unfix

private val RxFunctorInstance : Functor<Rx> =
    object : ShadyFunctor<Rx> {

        override fun <A, B> Kind<Rx, A>.fmap(f: (A) -> B): Kind<Rx, B> =
            fix().map(f).unfix()

    }

val Rx.Companion.Functor
    get() = RxFunctorInstance
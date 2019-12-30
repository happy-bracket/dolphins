package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.ShadyFunctor
import dolphins.rx.types.ForRx
import dolphins.rx.types.Rx
import dolphins.rx.types.fix
import dolphins.rx.types.unfix

private val RxFunctorInstance : Functor<ForRx> =
    object : ShadyFunctor<ForRx> {

        override fun <A, B> Kind<ForRx, A>.fmap(f: (A) -> B): Kind<ForRx, B> =
            fix().map(f).unfix()

    }

val Rx.Companion.Functor
    get() = RxFunctorInstance
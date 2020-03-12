package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Applicative
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.ShadyApplicative
import dolphins.rx.types.ForRx
import dolphins.rx.types.Rx
import dolphins.rx.types.fix
import dolphins.rx.types.unfix
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

private val RxApplicativeInstance: Applicative<ForRx> =
    object : ShadyApplicative<ForRx>, Functor<ForRx> by Rx.Functor {

        override fun <A, B, C> zip(a: Kind<ForRx, A>, b: Kind<ForRx, B>, f: (A, B) -> C): Kind<ForRx, C> =
            Observable.zip(a.fix(), b.fix(), BiFunction(f)).unfix()

    }

val Rx.Companion.Applicative
    get() = RxApplicativeInstance
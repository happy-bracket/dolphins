package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Applicative
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.ShadyApplicative
import dolphins.rx.types.Rx
import dolphins.rx.types.RxVal
import dolphins.rx.types.fix
import dolphins.rx.types.unfix
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

private val RxApplicativeInstance: Applicative<Rx> =
    object : ShadyApplicative<Rx>, Functor<Rx> by Rx.Functor {

        override fun <A, B, C> zip(a: Kind<Rx, A>, b: Kind<Rx, B>, f: (A, B) -> C): Kind<Rx, C> =
            Observable.zip(a.fix(), b.fix(), BiFunction(f)).unfix()

    }

val Rx.Companion.Applicative
    get() = RxApplicativeInstance
package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Applicative
import dolphins.foundation.typeclasses.Monad
import dolphins.rx.types.Rx
import dolphins.rx.types.RxVal
import dolphins.rx.types.fix
import dolphins.rx.types.unfix
import io.reactivex.Observable

private val RxMonadInstance: Monad<Rx> =
    object : Monad<Rx>, Applicative<Rx> by Rx.Applicative {

        override fun <A> just(value: A): Kind<Rx, A> =
            Observable.just(value).unfix()

        override fun <A, B> Kind<Rx, A>.flatMap(f: (A) -> Kind<Rx, B>): Kind<Rx, B> =
            fix().flatMap { f(it).fix() }.unfix()

    }

val Rx.Companion.Monad
    get() = RxMonadInstance
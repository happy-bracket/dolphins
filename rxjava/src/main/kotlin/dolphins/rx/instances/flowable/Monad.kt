package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Applicative
import dolphins.foundation.typeclasses.Functor
import dolphins.foundation.typeclasses.Monad
import dolphins.rx.types.ForRx
import dolphins.rx.types.Rx
import dolphins.rx.types.fix
import dolphins.rx.types.unfix
import io.reactivex.Observable

private val RxMonadInstance: Monad<ForRx> =
    object : Monad<ForRx>, Applicative<ForRx> by Rx.Applicative {

        override fun <A> just(value: A): Kind<ForRx, A> =
            Observable.just(value).unfix()

        override fun <A, B> Kind<ForRx, A>.flatMap(f: (A) -> Kind<ForRx, B>): Kind<ForRx, B> =
            fix().flatMap { f(it).fix() }.unfix()

    }

val Rx.Companion.Monad
    get() = RxMonadInstance
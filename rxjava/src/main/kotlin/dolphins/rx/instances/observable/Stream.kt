package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Monad
import dolphins.foundation.typeclasses.Stream
import dolphins.rx.types.*
import io.reactivex.Observable

private val RxStreamInstance: Stream<Rx> =
    object : Stream<Rx>, Monad<Rx> by Rx.Monad {

        override fun <A, B> Kind<Rx, A>.scan(acc: B, f: (B, A) -> B): Kind<Rx, B> =
            fixed { scan(acc, f) }

        override fun <A> merge(vararg ss: Kind<Rx, A>): Kind<Rx, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

        override fun <A> merge(ss: List<Kind<Rx, A>>): Kind<Rx, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

        override fun <A> Kind<Rx, A>.take(number: Int): Kind<Rx, A> =
            fixed { take(number.toLong()) }

        override fun <A, B> Kind<Rx, A>.concatMapEager(f: (A) -> Kind<Rx, B>): Kind<Rx, B> =
            fixed {
                concatMapEager {
                    f(it).fix()
                }
            }

    }

val Rx.Companion.Stream
    get() = RxStreamInstance
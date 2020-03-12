package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Monad
import dolphins.foundation.typeclasses.Stream
import dolphins.rx.types.*
import io.reactivex.Observable

private val RxStreamInstance: Stream<ForRx> =
    object : Stream<ForRx>, Monad<ForRx> by Rx.Monad {

        override fun <A, B> Kind<ForRx, A>.scan(acc: B, f: (B, A) -> B): Kind<ForRx, B> =
            fixed { scan(acc, f) }

        override fun <A> merge(vararg ss: Kind<ForRx, A>): Kind<ForRx, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

        override fun <A> merge(ss: List<Kind<ForRx, A>>): Kind<ForRx, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

        override fun <A> Kind<ForRx, A>.take(number: Int): Kind<ForRx, A> =
            fixed { take(number.toLong()) }

    }

val Rx.Companion.Stream
    get() = RxStreamInstance
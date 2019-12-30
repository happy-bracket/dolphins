package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Monad
import dolphins.foundation.typeclasses.Stream
import dolphins.rx.types.ForRx
import dolphins.rx.types.Rx
import dolphins.rx.types.fix
import dolphins.rx.types.unfix
import io.reactivex.Observable

private val RxStreamInstance: Stream<ForRx> =
    object : Stream<ForRx>, Monad<ForRx> by Rx.Monad {

        override fun <A, B> Kind<ForRx, A>.scan(acc: B, f: (B, A) -> B): Kind<ForRx, B> =
            fix().scan(acc, f).unfix()

        override fun <A> merge(vararg ss: Kind<ForRx, A>): Kind<ForRx, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

        override fun <A> merge(ss: List<Kind<ForRx, A>>): Kind<ForRx, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

    }

val Rx.Companion.Stream
    get() = RxStreamInstance
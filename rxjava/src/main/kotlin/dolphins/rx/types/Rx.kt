package dolphins.rx.types

import dolphins.foundation.Kind
import io.reactivex.Observable

class ForRx private constructor()
typealias RxOf<A> = Kind<ForRx, A>

class Rx<A>(val flow: Observable<A>) : RxOf<A> {
    companion object
}

fun <A> RxOf<A>.fix() = (this as Rx<A>).flow
fun <A> Observable<A>.unfix() = Rx(this)
fun <A, B> RxOf<A>.fixed(f: Observable<A>.() -> Observable<B>): RxOf<B> =
    fix().let(f).unfix()
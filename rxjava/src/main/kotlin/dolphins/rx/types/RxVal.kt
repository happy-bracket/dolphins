package dolphins.rx.types

import dolphins.foundation.Kind
import io.reactivex.Observable

class Rx private constructor() {
    companion object
}
typealias RxOf<A> = Kind<Rx, A>

class RxVal<A>(val flow: Observable<A>) : RxOf<A>

fun <A> RxOf<A>.fix() = (this as RxVal<A>).flow
fun <A> Observable<A>.unfix() = RxVal(this)
fun <A, B> RxOf<A>.fixed(f: Observable<A>.() -> Observable<B>): RxOf<B> =
    fix().let(f).unfix()
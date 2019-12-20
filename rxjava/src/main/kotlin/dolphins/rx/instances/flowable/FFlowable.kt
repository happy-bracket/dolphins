package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import io.reactivex.Observable

class ForFlowable private constructor()
typealias FlowableOf<A> = Kind<ForFlowable, A>

class FFlowable<A>(val flow: Observable<A>) : FlowableOf<A>

fun <A> FlowableOf<A>.fix() = (this as FFlowable<A>).flow
fun <A> Observable<A>.unfix() = FFlowable(this)
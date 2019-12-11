package ru.hbracket.dolphins.rx.flowable

import io.reactivex.rxjava3.core.Observable
import ru.hbracket.dolphins.f.Kind

class ForFlowable private constructor()
typealias FlowableOf<A> = Kind<ForFlowable, A>

data class FFlowable<A>(val flow: Observable<A>) : FlowableOf<A>

fun <A> FlowableOf<A>.fix() = (this as FFlowable<A>).flow
fun <A> Observable<A>.unfix() = FFlowable(this)
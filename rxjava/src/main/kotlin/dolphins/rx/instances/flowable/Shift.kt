package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Shift
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

val FlowableShift: Shift<ForFlowable, Scheduler> =
    object : Shift<ForFlowable, Scheduler> {

        override fun computation(): Scheduler =
            Schedulers.computation()

        override fun io(): Scheduler =
            Schedulers.io()

        override fun <A> Kind<ForFlowable, A>.shiftTo(dispatcher: Scheduler): Kind<ForFlowable, A> =
            fix().observeOn(dispatcher).unfix()

    }
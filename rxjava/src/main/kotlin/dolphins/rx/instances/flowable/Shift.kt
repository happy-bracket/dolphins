package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.ExecContext
import dolphins.foundation.typeclasses.Shift
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

val FlowableShift: Shift<ForFlowable> =
    object : Shift<ForFlowable> {

        override fun computation(): ExecContext<ForFlowable> =
            RxContext(Schedulers.computation())

        override fun io(): ExecContext<ForFlowable> =
            RxContext(Schedulers.io())

        override fun <A> Kind<ForFlowable, A>.shiftTo(execContext: ExecContext<ForFlowable>): Kind<ForFlowable, A> =
            fix().observeOn(execContext.fix()).unfix()

    }

class RxContext(val scheduler: Scheduler) : ExecContext<ForFlowable>

fun ExecContext<ForFlowable>.fix() = (this as RxContext).scheduler
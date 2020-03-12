package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.ExecContext
import dolphins.foundation.typeclasses.Shift
import dolphins.rx.types.*
import io.reactivex.schedulers.Schedulers

private val RxShiftInstance: Shift<ForRx> =
    object : Shift<ForRx> {

        override fun computation(): ExecContext<ForRx> =
            RxContext(Schedulers.computation())

        override fun io(): ExecContext<ForRx> =
            RxContext(Schedulers.io())

        override fun <A> Kind<ForRx, A>.shiftTo(execContext: ExecContext<ForRx>): Kind<ForRx, A> =
            fix().observeOn(execContext.fix()).unfix()

    }

val Rx.Companion.Shift
    get() = RxShiftInstance
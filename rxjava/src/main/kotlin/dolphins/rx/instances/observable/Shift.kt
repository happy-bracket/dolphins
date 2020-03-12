package dolphins.rx.instances.observable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.ExecContext
import dolphins.foundation.typeclasses.Shift
import dolphins.rx.types.*
import io.reactivex.schedulers.Schedulers

private val RxShiftInstance: Shift<Rx> =
    object : Shift<Rx> {

        override fun computation(): ExecContext<Rx> =
            RxContext(Schedulers.computation())

        override fun io(): ExecContext<Rx> =
            RxContext(Schedulers.io())

        override fun single(): ExecContext<Rx> =
            RxContext(Schedulers.single())

        override fun <A> Kind<Rx, A>.shiftTo(execContext: ExecContext<Rx>): Kind<Rx, A> =
            fix().observeOn(execContext.fix()).unfix()

    }

val Rx.Companion.Shift
    get() = RxShiftInstance
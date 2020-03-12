package dolphins.rx.types

import dolphins.foundation.typeclasses.ExecContext
import io.reactivex.Scheduler

class RxContext(val scheduler: Scheduler) : ExecContext<Rx>

fun ExecContext<Rx>.fix() = (this as RxContext).scheduler
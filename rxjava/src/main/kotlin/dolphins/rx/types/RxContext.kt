package dolphins.rx.types

import dolphins.foundation.typeclasses.ExecContext
import io.reactivex.Scheduler

class RxContext(val scheduler: Scheduler) : ExecContext<ForRx>

fun ExecContext<ForRx>.fix() = (this as RxContext).scheduler
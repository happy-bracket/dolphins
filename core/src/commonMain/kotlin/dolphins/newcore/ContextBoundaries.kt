package dolphins.newcore

import dolphins.foundation.typeclasses.ExecContext

class ContextBoundaries<F>(
    val update: ExecContext<F>
)
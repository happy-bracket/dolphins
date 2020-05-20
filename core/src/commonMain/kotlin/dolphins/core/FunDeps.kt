package dolphins.core

import dolphins.foundation.typeclasses.*

class FunDepsBag<G>(
    val channel: Channel<G>,
    val shift: Shift<G>,
    val consume: Consume<G>,
    val stream: Stream<G>
) : FunDeps<G>, Channel<G> by channel, Stream<G> by stream, Shift<G> by shift, Consume<G> by consume

interface FunDeps<G> : Channel<G>, Stream<G>, Shift<G>, Consume<G>

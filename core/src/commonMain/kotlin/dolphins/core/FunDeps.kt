package dolphins.core

import dolphins.foundation.typeclasses.*

class FunDepsBag<G>(
    private val channel: Channel<G>,
    private val shift: Shift<G>,
    private val consume: Consume<G>,
    private val stream: Stream<G>
) : FunDeps<G>, Channel<G> by channel, Stream<G> by stream, Shift<G> by shift, Consume<G> by consume

interface FunDeps<G> : Channel<G>, Stream<G>, Shift<G>, Consume<G>

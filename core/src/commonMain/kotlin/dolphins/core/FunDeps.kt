package dolphins.core

import dolphins.foundation.typeclasses.*

class FunDepsBag<G, H : Handle>(
    private val channel: Channel<G>,
    private val shift: Shift<G>,
    private val consume: Consume<G, H>,
    private val stream: Stream<G>
) : FunDeps<G, H>, Channel<G> by channel, Stream<G> by stream, Shift<G> by shift, Consume<G, H> by consume

interface FunDeps<G, H : Handle> : Channel<G>, Stream<G>, Shift<G>, Consume<G, H>

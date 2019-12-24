package dolphins.core

import dolphins.foundation.typeclasses.*

class FunDepsBag<G, D, H : Handle>(
    private val channel: Channel<G>,
    private val shift: Shift<G, D>,
    private val consume: Consume<G, H>,
    private val stream: Stream<G>
) : FunDeps<G, D, H>, Channel<G> by channel, Stream<G> by stream, Shift<G, D> by shift, Consume<G, H> by consume

interface FunDeps<G, D, H : Handle> : Channel<G>, Stream<G>, Shift<G, D>, Consume<G, H>

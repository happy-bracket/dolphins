package dolphins.newcore

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Consume
import dolphins.foundation.typeclasses.Stream

/**
 * Represents an entity which can receive events of type [Ev] and expose state of type [St] for observation
 * @see Dolphin - main implementation
 */
interface Feature<F, St, Ev, Mu, Ef> : Consume<F>, Stream<F> {

    fun offer(event: Ev): Kind<F, Unit>

    fun state(): Kind<F, St>

}

typealias PureFeature<F, St, Ev, Mu> = Feature<F, St, Ev, Mu, Nothing>

typealias MonoFeature<F, St, Mu, Ef> = Feature<F, St, Mu, Mu, Ef>

typealias PureMonoFeature<F, St, Mu> = Feature<F, St, Mu, Mu, Nothing>
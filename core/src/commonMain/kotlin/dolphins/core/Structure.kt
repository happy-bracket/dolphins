package dolphins.core

import dolphins.foundation.types.channel.ChannelVal

/**
 * Represents what will be going on in [Feature]
 * [core], [coeffectHandler] and [effectHandler] are exposed for change,
 * while [deps] and channels are only available for observation.
 */
data class Structure<F, St, Ev, Mu, Ef>(
    private val deps: FunDeps<F>,
    val core: Core<St, Mu, Ef>,
    val coeffectHandler: Handler<F, Ev, Mu>,
    val effectHandler: Handler<F, Ef, Ev>,
    private val eventChannel: ChannelVal<F, Ev>,
    private val mutationChannel: ChannelVal<F, Mu>,
    private val stateChannel: ChannelVal<F, St>,
    private val effectChannel: ChannelVal<F, Set<Ef>>
) {

    fun <R> liftF(action: FunDeps<F>.() -> R): R =
        deps.run(action)

    fun observeEvents() = eventChannel
    fun observeMutations() = mutationChannel
    fun observeState() = stateChannel
    fun observeEffects() = effectChannel

}
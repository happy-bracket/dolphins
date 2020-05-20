package dolphins.newcore

data class Splinter<St, Mu, Ef>(
    override val initialState: St,
    override val initialEffects: Set<Ef>,
    override val update: (St, Mu) -> Pair<St, Set<Ef>>
) : HasInitialState<St>, HasInitialEffects<Ef>, HasUpdate<St, Mu, Ef>

data class CoreWithHandler<F, St, Mu, Ef>(
    val splinter: Splinter<St, Mu, Ef>,
    override val handler: Handler<F, Ef, Mu>
) : HasInitialState<St> by splinter,
    HasInitialEffects<Ef> by splinter,
    HasUpdate<St, Mu, Ef> by splinter,
    HasHandler<F, Ef, Mu>

data class CoreWithCoHandler<F, St, Ev, Mu, Ef>(
    val splinter: Splinter<St, Mu, Ef>,
    override val coHandler: Handler<F, Ev, Mu>
) : HasInitialState<St> by splinter,
    HasInitialEffects<Ef> by splinter,
    HasUpdate<St, Mu, Ef> by splinter,
    HasCoHandler<F, Ev, Mu>

data class Core<F, St, Ev, Mu, Ef>(
    override val initialState: St,
    override val initialEffects: Set<Ef>,
    override val update: (St, Mu) -> Pair<St, Set<Ef>>,
    override val coHandler: Handler<F, Ev, Mu>,
    override val handler: Handler<F, Ef, Ev>
) : HasInitialState<St>,
    HasInitialEffects<Ef>,
    HasUpdate<St, Mu, Ef>,
    HasHandler<F, Ef, Ev>,
    HasCoHandler<F, Ev, Mu>
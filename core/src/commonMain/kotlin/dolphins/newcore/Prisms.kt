package dolphins.newcore

interface HasInitialState<St> {
    val initialState: St
}

interface HasInitialEffects<Ef> {
    val initialEffects: Set<Ef>
}

interface HasUpdate<St, Mu, Ef> {
    val update: (St, Mu) -> Pair<St, Set<Ef>>
}

interface HasCoHandler<F, Ev, Mu> {
    val coHandler: Handler<F, Ev, Mu>
}

interface HasHandler<F, Ef, Ev> {
    val handler: Handler<F, Ef, Ev>
}
package dolphins.newcore

data class Splinter<St, Mu, Ef>(
    val initialState: St,
    val initialEffects: Set<Ef>,
    val update: (St, Mu) -> Pair<St, Set<Ef>>
)

data class Core<F, St, Ev, Mu, Ef>(
    val initialState: St,
    val initialEffects: Set<Ef>,
    val update: (St, Mu) -> Pair<St, Set<Ef>>,
    val coHandler: Handler<F, Ev, Mu>,
    val handler: Handler<F, Ef, Ev>
)

typealias PureSplinter<St, Mu> = Splinter<St, Mu, Nothing>

fun <St, Mu> PureSplinter(
    initialState: St,
    update: (St, Mu) -> St
): PureSplinter<St, Mu> = Splinter(
    initialState,
    emptySet()
) { st, mu ->
    update(st, mu) to emptySet()
}
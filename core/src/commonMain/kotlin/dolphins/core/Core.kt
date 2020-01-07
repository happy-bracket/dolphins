package dolphins.core

data class Core<S, M, E>(
    val initialState: S,
    val initialEffects: Set<E>,
    val update: (S, M) -> Pair<S, Set<E>>
)

fun sss() {

}
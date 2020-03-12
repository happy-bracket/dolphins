package dolphins.newcore

/*

1. (S, M) -> S
2. (S, M) -> (S, E) -> H<E, M>
3. Ev -> H<Ev, M> -> (S, F<M>) -> (S, E) -> H<E, Ev>
4. Ev -> H<Ev, M> -> (S, F<M>) -> S

(S, M) -> S ~~ (S, M) -> (S, _|_) -> Idle
1 ~ 2

Ev -> H<Ev, M> -> (S, F<M>) -> (S, E) -> H<E, Ev> ~~ M -> Identity -> (S, M) -> (S, E) -> H<E, M>
3 ~ 2

 */

data class RCore<St, Mu, Ef>(
    val initialState: St,
    val initialEffects: Set<Ef>,
    val update: (St, Mu) -> Pair<St, Set<Ef>>
)
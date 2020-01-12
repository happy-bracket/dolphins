package dolphins.patterns

import dolphins.core.Core
import dolphins.core.Feature
import dolphins.core.FunDeps
import dolphins.core.Handler

/**
 * Special mutation, which eliminates the `M` type from `Feature` and instead is defined by state [S] and effect [E].
 * `Feature` with ghost mutations will look like this:
 * ```kotlin
 * typealias GhostFeature<F, S, Ev, E> = Feature<F, S, Ev, GhostMutation<S, E>, E>
 * ```
 */
data class GhostMutation<S, E>(
    val changeState: (S) -> S,
    val emitEffects: (S) -> Set<E>
)

fun <S, E> ghostUpdate(state: S, mutation: GhostMutation<S, E>): Pair<S, Set<E>> =
    mutation.changeState(state) to mutation.emitEffects(state)

typealias GhostCore<S, E> = Core<S, GhostMutation<S, E>, E>

fun <S, E> GhostCore(initialState: S, initialEffects: Set<E>): GhostCore<S, E> =
    Core(
        initialState,
        initialEffects,
        ::ghostUpdate
    )

typealias GhostFeature<F, S, Ev, E> = Feature<F, S, Ev, GhostMutation<S, E>, E>

fun <F, S, Ev, E> GhostingFeature(
    deps: FunDeps<F>,
    core: GhostCore<S, E>,
    coeffectHandler: Handler<F, Ev, GhostMutation<S, E>>,
    effectHandler: Handler<F, E, Ev>
): GhostFeature<F, S, Ev, E> =
    Feature(
        deps,
        core,
        coeffectHandler,
        effectHandler
    )
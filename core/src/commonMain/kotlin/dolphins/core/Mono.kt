package dolphins.core

import dolphins.foundation.Kind
import dolphins.foundation.functions.self

typealias MonoFeature<F, S, M, E> = Feature<F, S, S, M, M, E>

fun <F, S, M, E> MonoFeature(
    deps: FunDeps<F>,
    core: Core<S, M, E>,
    handler: Handler<F, E, M>
): MonoFeature<F, S, M, E> =
    Feature(
        deps,
        core,
        IdentityCofx(deps),
        handler
    )

fun <F, S> MonoFeature<F, S, *, *>.state(): Kind<F, S> =
    select(::self)
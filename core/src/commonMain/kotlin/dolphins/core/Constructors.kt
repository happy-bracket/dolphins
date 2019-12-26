package dolphins.core

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
package dolphins.newcore

import dolphins.core.FunDeps
import dolphins.core.FunDepsBag

object Evolution {

    fun <F, St, Mu> Directly(funDeps: FunDepsBag<F>, splinter: PureSplinter<St, Mu>): PureMonoFeature<F, St, Mu> {
        return Dolphin<F, St, Mu, Mu, Nothing>(
            funDeps,
            Core(
                splinter.initialState,
                emptySet(),
                splinter.update,
                Handler.Identity(funDeps.stream),
                Handler.Idle(funDeps.stream)
            )
        )
    }

    fun <F, St, Ev, Mu, Ef> Directly(funDeps: FunDepsBag<F>, core: Core<F, St, Ev, Mu, Ef>): Feature<F, St, Ev, Mu, Ef> {
        return Dolphin(
            funDeps,
            core
        )
    }

}
package dolphins.core

import dolphins.foundation.Kind

interface Handler<F, in E, out M> : FunDeps<F> {

    fun handle(e: E): Kind<F, M>

}

class IdentityCofx<F, M>(funDeps: FunDeps<F>) : Handler<F, M, M>, FunDeps<F> by funDeps {

    override fun handle(e: M): Kind<F, M> =
        just(e)

}
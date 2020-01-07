package dolphins.core

import dolphins.foundation.Kind

interface Handler<F, in E, out M> : FunDeps<F> {

    /**
     * DO NOT OVERRIDE. This method is for internal uses only.
     */
    fun kindfulHandle(e: E): Kind<F, M>

}

fun <F, E, M> handler(deps: FunDeps<F>, handle: (E) -> Kind<F, M>): Handler<F, E, M> =
    object : Handler<F, E, M>, FunDeps<F> by deps {

        override fun kindfulHandle(e: E): Kind<F, M> =
            handle(e)

    }

fun <F, E1, E2, M> Handler<F, E2, M>.preMap(handler: Handler<F, E1, E2>): Handler<F, E1, M> =
    handler(this) { e1 ->
        handler.kindfulHandle(e1)
            .flatMap { e2 ->
                this.kindfulHandle(e2)
            }
    }

fun <F, E, M1, M2> Handler<F, E, M1>.postMap(handler: Handler<F, M1, M2>): Handler<F, E, M2> =
    handler(this) { e ->
        this.kindfulHandle(e)
            .flatMap { m ->
                handler.kindfulHandle(m)
            }
    }

class IdentityCofx<F, M>(funDeps: FunDeps<F>) : Handler<F, M, M>, FunDeps<F> by funDeps {

    override fun kindfulHandle(e: M): Kind<F, M> =
        just(e)

}
package dolphins.foundation.typeclasses

import dolphins.foundation.Kind2
import dolphins.foundation.functions.self

interface BiFunctor<F> {

    fun <A, A1, B> Kind2<F, A, B>.lmap(f: (A) -> A1): Kind2<F, A1, B>

    fun <A, B, B1> Kind2<F, A, B>.rmap(f: (B) -> B1): Kind2<F, A, B1>

    fun <A, A1, B, B1> Kind2<F, A, B>.bimap(af: (A) -> A1, bf: (B) -> B1): Kind2<F, A1, B1>

}

interface BiFunctorBimap<F> : BiFunctor<F> {

    override fun <A, A1, B, B1> Kind2<F, A, B>.bimap(af: (A) -> A1, bf: (B) -> B1): Kind2<F, A1, B1> =
        lmap(af).rmap(bf)

}

interface BiFunctorRLMap<F> : BiFunctor<F> {

    override fun <A, A1, B> Kind2<F, A, B>.lmap(f: (A) -> A1): Kind2<F, A1, B> =
        bimap(f, ::self)

    override fun <A, B, B1> Kind2<F, A, B>.rmap(f: (B) -> B1): Kind2<F, A, B1> =
        bimap(::self, f)

}
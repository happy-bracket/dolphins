package ru.hbracket.dolphins.f.ts

import ru.hbracket.dolphins.f.Kind
import ru.hbracket.dolphins.f.ShadyException

interface Functor<F> {

    fun <A, B> lift(f: (A) -> B): (Kind<F, A>) -> Kind<F, B>

    fun <A, B> Kind<F, A>.fmap(f: (A) -> B): Kind<F, B> =
        lift(f)(this)

}

interface ShadyFunctor<F> : Functor<F> {

    override fun <A, B> lift(f: (A) -> B): (Kind<F, A>) -> Kind<F, B> =
        throw ShadyException("Functor")

    override fun <A, B> Kind<F, A>.fmap(f: (A) -> B): Kind<F, B>

}
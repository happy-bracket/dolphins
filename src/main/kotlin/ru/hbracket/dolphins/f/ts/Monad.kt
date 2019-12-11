package ru.hbracket.dolphins.f.ts

import ru.hbracket.dolphins.f.Kind

interface Monad<F> : Functor<F> {

    fun <A> just(value: A): Kind<F, A>

    fun <A, B> Kind<F, A>.flatMap(f: (A) -> Kind<F, B>): Kind<F, B>

}
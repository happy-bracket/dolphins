package ru.hbracket.dolphins.f.ts

import ru.hbracket.dolphins.f.Kind

interface Consume<F, H : Handle> {

    fun <A> Kind<F, A>.consume(f: (A) -> Unit): H

}

interface Handle {

    fun release()

}
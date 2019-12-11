package ru.hbracket.dolphins.f

interface Kind<out F, out A>
typealias Kind2<F, A, B> = Kind<F, Kind<A, B>>
package dolphins.foundation

interface Kind<out F, out A>
typealias Kind2<F, A, B> = Kind<F, Kind<A, B>>
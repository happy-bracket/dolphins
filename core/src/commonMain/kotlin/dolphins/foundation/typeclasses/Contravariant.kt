package dolphins.foundation.typeclasses

import dolphins.foundation.Kind

interface Contravariant<F> {

    fun <A, B> Kind<F, B>.preMap(f: (A) -> B): Kind<F, A>

}
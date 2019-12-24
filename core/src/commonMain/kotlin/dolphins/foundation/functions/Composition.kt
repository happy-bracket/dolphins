package dolphins.foundation.functions

fun <A, B, C> compose(bc: (B) -> C, ab: (A) -> B): (A) -> C =
    {
        bc(ab(it))
    }
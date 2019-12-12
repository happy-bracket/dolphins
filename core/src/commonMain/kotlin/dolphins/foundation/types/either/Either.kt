package dolphins.foundation.types.either

sealed class Either<out L, out R> {

    data class Left<L>(val value: L) : Either<L, Nothing>()
    data class Right<R>(val value: R) : Either<Nothing, R>()

    fun <C> fold(ifLeft: (L) -> C, ifRight: (R) -> C): C =
        when (this) {
            is Left -> ifLeft(value)
            is Right -> ifRight(value)
        }

    companion object
}

fun <T> T.left() = Either.Left(this)
fun <T> T.right() = Either.Right(this)
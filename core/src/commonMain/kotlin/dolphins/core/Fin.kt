package dolphins.core

/**
 * Represents abstract middleware.
 * [Fin] can see into internal [Structure] of [Feature], making dirty changes to it (although, without altering types)
 * or simply observing, and emitting a new structure.
 * On creation, [Feature] runs all [Fin]s that it is passed to, computing final [Structure] that it will work upon.
 */
interface Fin<F, St, Ev, Mu, Ef> {

    fun examine(structure: Structure<F, St, Ev, Mu, Ef>): Structure<F, St, Ev, Mu, Ef>

}

typealias AgnosticFin = Fin<Any?, Any?, Any?, Any?, Any?>
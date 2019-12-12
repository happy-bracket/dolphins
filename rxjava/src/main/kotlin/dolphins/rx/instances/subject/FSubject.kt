package dolphins.rx.instances.subject

import dolphins.foundation.Kind
import io.reactivex.subjects.Subject

class ForSubject private constructor()
typealias SubjectOf<A> = Kind<ForSubject, A>

data class FSubject<A>(val subject: Subject<A>): Kind<ForSubject, A>

fun <A> SubjectOf<A>.fix() = (this as FSubject<A>).subject
fun <A> Subject<A>.unfix() = FSubject(this)
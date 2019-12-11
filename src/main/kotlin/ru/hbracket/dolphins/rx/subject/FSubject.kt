package ru.hbracket.dolphins.rx.subject

import io.reactivex.rxjava3.subjects.Subject
import ru.hbracket.dolphins.f.Kind

class ForSubject private constructor()
typealias SubjectOf<A> = Kind<ForSubject, A>

data class FSubject<A>(val subject: Subject<A>): Kind<ForSubject, A>

fun <A> SubjectOf<A>.fix() = (this as FSubject<A>).subject
fun <A> Subject<A>.unfix() = FSubject(this)
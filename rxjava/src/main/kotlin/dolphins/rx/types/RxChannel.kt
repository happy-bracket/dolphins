package dolphins.rx.types

import dolphins.foundation.types.channel.ChannelVal
import io.reactivex.subjects.Subject

class RxChannel<A>(val subject: Subject<A>) : ChannelVal<ForRx, A>

fun <A> ChannelVal<ForRx, A>.fix(): RxChannel<A> = this as RxChannel
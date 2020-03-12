package dolphins.rx.types

import dolphins.foundation.types.channel.ChannelVal
import io.reactivex.subjects.Subject

class RxChannel<A>(val subject: Subject<A>) : ChannelVal<Rx, A>

fun <A> ChannelVal<Rx, A>.fix(): RxChannel<A> = this as RxChannel
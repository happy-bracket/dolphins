package dolphins.foundation.typeclasses

import dolphins.foundation.Kind
import dolphins.foundation.types.channel.ChannelVal

interface Channel<F> {

    fun <A> through(): ChannelVal<F, A>

    fun <A> conflated(): ChannelVal<F, A>
    fun <A> conflated(default: A): ChannelVal<F, A>

    fun <A> ChannelVal<F, A>.write(value: A): Kind<F, Unit>

    fun <A> ChannelVal<F, A>.suspendRead(): Kind<F, A>

}
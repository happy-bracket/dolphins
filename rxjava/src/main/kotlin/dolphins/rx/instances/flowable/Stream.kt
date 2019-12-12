package dolphins.rx.instances.flowable

import dolphins.foundation.Kind
import dolphins.foundation.typeclasses.Monad
import dolphins.foundation.typeclasses.Stream
import io.reactivex.Observable

val FlowableStream: Stream<ForFlowable> =
    object : Stream<ForFlowable>, Monad<ForFlowable> by FlowableMonad {

        override fun <A, B> Kind<ForFlowable, A>.scan(acc: B, f: (B, A) -> B): Kind<ForFlowable, B> =
            fix().scan(acc, f).unfix()

        override fun <A> merge(vararg ss: Kind<ForFlowable, A>): Kind<ForFlowable, A> =
            Observable.merge(ss.map { it.fix() }).unfix()

    }
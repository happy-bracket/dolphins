package dolphins.newcore

import dolphins.foundation.Kind

interface Handler<F, in In, out Out> {

    fun fHandle(input: In): Kind<F, Out>

}
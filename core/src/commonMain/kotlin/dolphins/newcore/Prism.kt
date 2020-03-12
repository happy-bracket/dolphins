package dolphins.newcore

interface DirectPrism<F, St, Ev, Mu, Ef> {

    fun refract(core: Core<F, St, Ev, Mu, Ef>): Core<F, St, Ev, Mu, Ef>

}

interface ForallPrism {

    fun <F, St, Ev, Mu, Ef, F1, St1, Ev1, Mu1, Ef1> refract(core: Core<F, St, Ev, Mu, Ef>): Core<F1, St1, Ev1, Mu1, Ef1>

}

interface FixedPrism<F, St, Ev, Mu, Ef> {

    fun <F1, St1, Ev1, Mu1, Ef1> refract(core: Core<F, St, Ev, Mu, Ef>): Core<F1, St1, Ev1, Mu1, Ef1>

}
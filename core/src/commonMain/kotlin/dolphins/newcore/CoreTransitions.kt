package dolphins.newcore

fun <F, St, Ev, Mu, Ef> Splinter<St, Mu, Ef>.withCoHandler(coHandler: Handler<F, Ev, Mu>): CoreWithCoHandler<F, St, Ev, Mu, Ef> =

# 🐬 Dolphins!
Dolphins is intended to be zero-dependency (hence multiplatform by default), highly customizable library (framework?)
for writing applications using declarative architecture named **The Elm Architecture**.

## Questions that might come up
- Why is there RxJava in dependencies?
RxJava was needed for a sample, to check that typing is correct. In the future, it needs to be moved to another module
(along with Coroutines, Java Concurrent and anything you might think of to work with async)
- What the hell are all those letters in the Feature class?
As aforesaid, Dolphins is zero-dependency and highly customizable. All those letters let you choose what type of effects you need in your app,
be it rx.Single, rx.Observable or Coroutine Flow. This is possible via a certain trick, called HKT, typeclasses and a little
alchemy to make it work on JVM (see **KindedJ**, **Arrow Kt**). End-users of the library will not need to be worry about all that, they will be supplied
with digestible contract depending on what flavor of library they choose.
- How exactly does flavor switch happen?
The switch happens through providing instances for typeclasses, defined in `ru.hbracket.f`. Those typeclasses declare basic
primitive operations that `Observable`-s or `Flow` can do, such as `map`, `flatMap` etc. After that, Dolphins handle the rest.
- Is it bound to Android only?
No. You can use Dolphins in Web, Desktop and iOS, it only needs that you provide concurrent primitives for that platform.
Most popular choices (Rx*, Coroutines) are planned to be provided by default.

## What is Dolphins in the long run?
It is in very early stage and depending on my ability or wish to develop it, it is hard to tell yet.
I am planning on adding time-travel, navigation, integrations with platforms where state persistence is crucial (Android).
Feature requests are welcome.

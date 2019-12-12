# 🐬 Dolphins!
Dolphins is intended to be zero-dependency (hence multiplatform by default), highly customizable library (framework?)
for writing applications using declarative architecture named **The Elm Architecture** (TEA for short).

## What is TEA?
Very generally, TEA allows you to encode your UI state management in terms of immutable (or persistent) data structures and pure functions, then supply those to some system, which is able to perform side effects (for Dolphins, it's `dolphins.core.Feature`) and... that's all.

Let's see an example. This is a simple anonymous chat, where messages are received through, maybe, WebSocket (it is actually not relevant at all right now). The user can type in his own message and send them.
```
data class ChatState(
  val messages: List<Message>,
  val input: String
)

sealed class ChatMutation {
  data class NewMessage(val message: Message) : ChatMutation()
  data class NewInput(val input: String) : ChatMutation()
  object SendMessage : ChatMutation()
}

sealed class ChatEffect {
  data class SendMessage(val text: String) : ChatEffect()
  object SubscribeToChat : ChatEffect()
}

fun ChatState.update(mutation: ChatMutation): Pair<ChatState, Set<ChatEffect>> =
  when (mutation) {
    is ChatMutation.NewMessage -> copy(messages = messages + mutation.message) to emptySet()
    is ChatMutation.NewInput -> copy(input = mutation.input) to emptySet()
    is ChatMutation.SendMessage -> copy(input = "") to setOf(ChatEffect.SendMessage(input))
  }
```
Here `ChatState` is a core immutable data class, which encodes relevant application state. `ChatMutation` is a subset of types which encode **what** changes your state. Function `update` describes **how** state changes in response to `ChatMutation`. `ChatEffect` then encodes some actions, which are either go-all-round or fire-and-forget. Examples include network requests and database interactions. Notice that those are also pure data classes - the actual activity happens in another entity, called "EffectHandler".
Then you take a `Feature` constructor of chosen flavor, shove it all in and then you have a complete working entity, which you can mutate and subscribe to its state to update your UI.

It's worth noting that nothing of that is platform-dependent and those you can transfer your whole interaction logic between platforms. All you need is to choose appropriate implementation of `Feature`. Although, you'd probably want to adapt the state you get to state that's most convenient to display on your UI. This task is accomplished through middlewares, which are on my roadmap.

Complete sample: https://gist.github.com/happy-bracket/8ca92b70d48d2ab4e2680b4ab318061c

## Questions that might come up
- **What the hell are all those letters in the Feature class?**
As aforesaid, Dolphins is zero-dependency and highly customizable. All those letters let you choose what type of effects you need in your app, be it rx.Single, rx.Observable or Coroutine Flow. This is possible via a certain trick, called HKT, typeclasses and a little
alchemy to make it work on JVM (see **KindedJ**, **Arrow Kt**). End-users of the library will not need to be worry about all that, they will be supplied with digestible contract depending on what flavor of library they choose.
- **How exactly does flavor switch happen?**
The switch happens through providing instances for typeclasses, defined in `dolphins.foundation.typeclasses`. Those typeclasses declare basic
primitive operations that `Observable`-s or `Flow` can do, such as `map`, `flatMap` etc. After that, Dolphins handle the rest.
- **Is it bound to Android only?**
No. You can use Dolphins in Web, Desktop and iOS, it only needs that you provide concurrent primitives for that platform.
Most popular choices (Rx*, Coroutines) are planned to be provided by default.

## What is Dolphins in the long run?
It is in very early stage and depending on my ability or wish to develop it.

Planned features:
- Time travel
- Logging
- Debugging utils
- Middlewares (hard to tell yet, how they will be implemented)
- Testing utils

Platforms to support:
- Kotlin Coroutines
- RxJS
- RxSwift (not sure, need to consult to iOS developers)

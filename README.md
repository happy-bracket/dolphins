# 🐬 Dolphins!
Dolphins is intended to be zero-dependency (hence multiplatform by default), highly customizable library (framework?)
for writing applications using declarative architecture named **The Elm Architecture** (TEA for short).

## What is TEA?
Very generally, TEA allows you to encode your UI state management in terms of immutable (or persistent) data structures and pure functions, then supply those to some system, which is able to perform side effects (for Dolphins, it's `dolphins.core.Feature`) and... that's all.

Let's see an example. This is a simple anonymous chat, where messages are received through, maybe, WebSocket (it is actually not relevant at all right now). The user can type in his own message and send them.
```kotlin
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
Then you take a `Feature` constructor of chosen flavor, shove it all in and then you have a complete working object, through which you can mutate the state and subscribe to its emission to update your UI.

It's worth noting that nothing of that is platform-dependent and thus you can transfer your whole interaction logic between platforms. All you need is to choose appropriate implementation of `Feature`. Although, you'd probably want to adapt the state you get to state that's most convenient to display on your UI.

Complete sample: https://gist.github.com/happy-bracket/8ca92b70d48d2ab4e2680b4ab318061c

## Features of 🐬 Dolphins
### Unopinionated
Usually we employ a certain set of libraries and patterns to write UI code. It might be reactivity along with RxJava, reactivity through MVVM with Lychee or LiveData. It especially varies among other platforms, where there's React or Angular or something else in Web, RxSwift on iOS and JavaFX with its own databinding on Desktop.

Well, Dolphins can handle most of that. Through a certain feature of type system called **HKT** (a lot of examples can be seen in arrow-kt.io), which abstracts over the type of effect. So, with a little effort (or no effort at all, depending on whether your favorite library is supported out-of-the-box), Your `Feature` can return `Observable<State>`, `Flow<State>` or you can even simply put a listener on it.
### Extensible
With middlewares (`dolphins.core.Fin`) you can polymorphically add new behaviors to `Feature`-s, such as logging, debugging mocks (or stubs, or fakes), time-travel and practically anything you want.
### Effectful
Handlers presented in Dolphins allow you to process effects separately from your actual pure business- or UI-logic. They are composable, such that you can create chains of Handlers with different types (proficient FP developers might recognize Kleisli arrows here - yes, exactly). Going further - you get Coeffect Handlers, which allow you to reduce the number of effects by pre-composing `update`. Samples to see how great it is will be out soon.

## What is Dolphins in the long run?
It is in very early stage and highly depends on my ability or wish to develop it.

Planned features:
- Time travel
- Logging
- Debugging utils
- Testing utils
- Predefined effects for networking and database

Platforms to support:
- Kotlin Coroutines
- RxJS
- RxSwift (not sure, need to consult to iOS developers)

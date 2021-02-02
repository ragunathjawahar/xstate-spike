package xstate

import kotlin.reflect.KClass

interface Hook {
  fun onName(name: String)
  fun onInitialState(initialState: KClass<out Any>)
  fun onState(state: KClass<out Any>)
  fun onTransition(event: KClass<out Any>, next: KClass<out Any>)
}

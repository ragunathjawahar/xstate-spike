package xstate

import kotlin.reflect.KClass
import xstate.hooks.DescriptionHook

class Machine<S : Any, E : Any, F>(
  private val name: String,
  private val initialState: KClass<out S>,
  private val hook: Hook,
  private val definition: Machine<S, E, F>.() -> Unit,
) {
  constructor(
    name: String,
    initialState: KClass<out S>,
    definition: Machine<S, E, F>.() -> Unit
  ) : this(name, initialState, DescriptionHook(), definition)

  fun states(
    builder: States<S, E>.() -> Unit
  ) {
    hook.onName(name)
    hook.onInitialState(initialState)
    builder(States(hook))
  }

  operator fun invoke(): Machine<S, E, F> {
    definition()
    return this
  }
}

class States<S : Any, E : Any>(
  private val hook: Hook
) {
  fun state(
    state: KClass<out S>,
    definition: State<S, E>.() -> Unit
  ) {
    hook.onState(state)
    definition(State(hook))
  }
}

class State<S : Any, E : Any>(
  private val hook: Hook
) {
  fun on(
    event: KClass<out E>,
    next: KClass<out S>
  ) {
    hook.onTransition(event, next)
  }
}

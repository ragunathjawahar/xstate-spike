package xstate

import kotlin.reflect.KClass

class Machine<S : Any, E : Any, F>(
  private val dslVisitor: DslVisitor,
  private val name: String,
  private val initialState: KClass<out S>,
  private val definition: Machine<S, E, F>.() -> Unit,
) {
  fun states(
    builder: States<S, E>.() -> Unit
  ) {
    dslVisitor.onName(name)
    dslVisitor.onInitialState(initialState)
    builder(States(dslVisitor))
  }

  operator fun invoke(): Machine<S, E, F> {
    definition()
    return this
  }
}

class States<S : Any, E : Any>(
  private val dslVisitor: DslVisitor
) {
  fun state(
    state: KClass<out S>,
    definition: State<S, E>.() -> Unit
  ) {
    dslVisitor.onState(state)
    definition(State(dslVisitor))
  }
}

class State<S : Any, E : Any>(
  private val dslVisitor: DslVisitor
) {
  fun on(
    event: KClass<out E>,
    next: KClass<out S>
  ) {
    dslVisitor.onTransition(event, next)
  }
}

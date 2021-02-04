package xstate

import kotlin.reflect.KClass
import xstate.visitors.DslVisitorWrapper
import xstate.visitors.mobius.Reducer
import xstate.visitors.mobius.Reducer.Companion.NoOpReducer

class Machine<S : Any, E : Any, F : Any> private constructor(
  private val name: String,
  private val initialState: KClass<out S>, // TODO Move this into the DSL
  private val definition: Machine<S, E, F>.() -> Unit
) {
  companion object {
    fun <S : Any, E : Any, F : Any> create(
      name: String,
      initialState: KClass<out S>,
      definition: Machine<S, E, F>.() -> Unit
    ): Machine<S, E, F> =
      Machine(name, initialState, definition)
  }

  private val visitorWrapper = DslVisitorWrapper()

  fun states(
    builder: States<S, E, F>.() -> Unit
  ) {
    visitorWrapper.onName(name)
    visitorWrapper.onInitialState(initialState)
    builder(States(visitorWrapper))
  }

  fun visit(visitor: DslVisitor) {
    visitorWrapper.visitor = visitor
    definition()
    visitorWrapper.visitor = null
  }
}

class States<S : Any, E : Any, F : Any>(
  private val dslVisitor: DslVisitor
) {
  fun state(
    state: KClass<out S>,
    definition: State<S, E, F>.() -> Unit
  ) {
    dslVisitor.onState(state)
    definition(State(dslVisitor))
  }
}

class State<S : Any, E : Any, F : Any>(
  private val dslVisitor: DslVisitor
) {
  fun on(
    event: KClass<out E>,
    next: KClass<out S>,
    effects: Set<KClass<out F>> = emptySet(),
    reducer: KClass<out Reducer<out S, out E>> = NoOpReducer::class
  ) {
    dslVisitor.onTransition(event, next, effects, reducer)
  }
}

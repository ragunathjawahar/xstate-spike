package xstate

import kotlin.reflect.KClass
import xstate.visitors.mobius.ReductionResult
import xstate.visitors.mobius.ReductionResult.Companion.NoOpReducer

class Machine<S : Any, E : Any, F : Any> private constructor(
  private val dslVisitor: DslVisitor,
  private val name: String,
  private val initialState: KClass<out S>, // TODO Move this into the DSL
) {
  companion object {
    fun <S : Any, E : Any, F : Any> create(
      dslVisitor: DslVisitor,
      name: String,
      initialState: KClass<out S>, // TODO Move this into the DSL
      definition: Machine<S, E, F>.() -> Unit,
    ): Machine<S, E, F> {
      return Machine<S, E, F>(dslVisitor, name, initialState)
        .apply { definition(this) }
    }
  }

  fun states(
    builder: States<S, E, F>.() -> Unit
  ) {
    dslVisitor.onName(name)
    dslVisitor.onInitialState(initialState)
    builder(States(dslVisitor))
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
    reducer: (S, E) -> ReductionResult = NoOpReducer
  ) {
    dslVisitor.onTransition(event, next, effects)
  }
}

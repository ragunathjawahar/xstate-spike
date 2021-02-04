package xstate

import kotlin.reflect.KClass
import xstate.visitors.DslVisitorWrapper
import xstate.visitors.mobius.Reducer
import xstate.visitors.mobius.Reducer.Companion.NoOpReducer

class Machine<S : Any, E : Any, F : Any> private constructor(
  private val name: String,
  private val definition: Machine<S, E, F>.() -> Unit
) {
  companion object {
    fun <S : Any, E : Any, F : Any> create(
      name: String,
      definition: Machine<S, E, F>.() -> Unit
    ): Machine<S, E, F> =
      Machine(name, definition)
  }

  private val visitorWrapper = DslVisitorWrapper()


  fun initial(
    state: KClass<out S>,
    effects: Set<KClass<out F>> = emptySet()
  ) {
    visitorWrapper.onName(name)
    visitorWrapper.onInitialState(state, effects)
  }

  fun state(
    state: KClass<out S>,
    definition: State<S, E, F>.() -> Unit
  ) {
    visitorWrapper.onState(state)
    definition(State(visitorWrapper))
  }

  fun <V : DslVisitor> visit(visitor: V): V {
    visitorWrapper.visitor = visitor
    definition()
    visitorWrapper.visitor = null
    return visitor
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

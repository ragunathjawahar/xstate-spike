package xstate.visitors.mobius

import kotlin.reflect.KClass
import xstate.DslVisitor
import xstate.visitors.mobius.Reducer.Companion.NoOpReducer

typealias State = Any
typealias Effect = KClass<out Any>

class MobiusVisitor : DslVisitor {
  private lateinit var initialStateClass: KClass<out Any>
  private lateinit var currentStateClass: KClass<out Any>
  private val transitions = mutableMapOf<Transition, TransitionStrategy>()

  // FIXME: 04/02/21 Use type inference to assign a type to the initial state
  val initialState: Any
    get() = initialStateClass.objectInstance!!

  // FIXME: 04/02/21 Replace this with a function with type parameters
  val updateFunction: (currentState: Any, event: Any) -> Any = { currentState, event ->
    val transitionKey = Transition(currentState::class, event::class)
    if (!transitions.containsKey(transitionKey)) {
      println("Missing transition ${currentState::class.simpleName} + ${event::class.simpleName}")
      currentState
    } else {
      val strategy = transitions[transitionKey]!!
      strategy.invoke(currentState, event)
    }
  }

  override fun onName(name: String) {
    /* No-op, nothing to do really. */
  }

  override fun onInitialState(initialState: KClass<out Any>) { // TODO: 02/02/21 Rename KClass parameters with Class suffix
    initialStateClass = initialState
  }

  override fun onState(state: KClass<out Any>) {
    currentStateClass = state
  }

  override fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>,
    reducer: KClass<out Reducer<out Any, out Any>>
  ) {
    if (reducer == NoOpReducer::class) {
      transitions[Transition(currentStateClass, event)] = ObjectStateObjectEffectTransitionStrategy(next, effects.first())
    } else {
      transitions[Transition(currentStateClass, event)] = ReducerStrategy(reducer)
    }
  }

  data class Transition(
    val currentStateClass: KClass<out Any>,
    val eventClass: KClass<out Any>
  )

  // Transition strategies
  // 1. Return Kotlin object
  // 2. Map event to copy constructor
  // 3. Use a custom reducer
}

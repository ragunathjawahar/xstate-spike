package xstate.visitors

import kotlin.reflect.KClass
import xstate.DslVisitor
import xstate.visitors.MobiusVisitor.ReductionResult.StateEffects

typealias State = Any
typealias Effects = Set<KClass<out Any>>

class MobiusVisitor : DslVisitor {
  private lateinit var initialStateClass: KClass<out Any>
  private lateinit var currentStateClass: KClass<out Any>
  private val transitions = mutableMapOf<Transition, Pair<State, Effects>>()

  val initialState: Any
    get() = initialStateClass.objectInstance!!

  val updateFunction: (currentState: Any, event: Any) -> Any = { currentState, event ->
    val transitionKey = Transition(currentState::class, event::class)
    if (transitions.containsKey(transitionKey)) {
      val (stateClass, effectClassesSet) = transitions[transitionKey]!!
      val nextState = (stateClass as KClass<out Any>).objectInstance!!
      val effects = effectClassesSet.map { it.objectInstance!! }.toSet()
      StateEffects(nextState, effects)
    } else {
      println("Missing transition ${currentState::class.simpleName} + ${event::class.simpleName}")
      currentState
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
    effects: Set<KClass<out Any>>
  ) {
    transitions[Transition(currentStateClass, event)] = Pair(next, effects)
  }

  data class Transition(
    val currentStateClass: KClass<out Any>,
    val eventClass: KClass<out Any>
  )

  sealed class ReductionResult {
    data class StateEffects(
      val state: Any,
      val effects: Set<Any>
    ) : ReductionResult()

    // TODO
    // 1. StateEffect
    // 2. Effect
    // 3. Effects
  }

  // Transition strategies
  // 1. Return Kotlin object
  // 2. Map event to copy constructor
  // 3. Use a custom reducer
}

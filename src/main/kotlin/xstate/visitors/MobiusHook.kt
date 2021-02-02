package xstate.visitors

import kotlin.reflect.KClass
import xstate.DslVisitor

typealias NextStateClass = KClass<out Any>

class MobiusVisitor : DslVisitor {
  private lateinit var initialStateClass: KClass<out Any>
  private lateinit var currentStateClass: KClass<out Any>
  private val transitions = mutableMapOf<Transition, NextStateClass>()

  val initialState: Any
    get() = initialStateClass.objectInstance!!

  val updateFunction: (currentState: Any, event: Any) -> Any = { currentState, event ->
    val transitionKey = Transition(currentState::class, event::class)
    if (transitions.containsKey(transitionKey)) {
      transitions[transitionKey]!!.objectInstance!!
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

  override fun onTransition(event: KClass<out Any>, next: KClass<out Any>) {
    transitions[Transition(currentStateClass, event)] = next
  }

  data class Transition(
    val currentStateClass: KClass<out Any>,
    val eventClass: KClass<out Any>
  )

  // Transition strategies
  // 1. Return Kotlin object
  // 2. Map event to copy constructor
}

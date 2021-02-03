package xstate.visitors.mobius

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import xstate.visitors.mobius.ReductionResult.StateEffect

interface TransitionStrategy {
  fun invoke(currentState: Any, event: Any): ReductionResult
}

class ObjectStateObjectEffectTransitionStrategy(
  private val stateClass: State,
  private val effectClass: Effect
) : TransitionStrategy {
  override fun invoke(currentState: Any, event: Any): ReductionResult {
    val nextState = (stateClass as KClass<out Any>).objectInstance!!
    /*
     * We may have to use the current state to derive event objects by mapping the state's properties to the
     * effect's properties.
     */
    val effect = if (effectClass.objectInstance != null) {
      debug("${effectClass.simpleName} is a Kotlin object, acquiring instance.")
      effectClass.objectInstance!!
    } else {
      debug("${effectClass.simpleName} is a class, attempting to construct a new object.")
      // Default parameters are not visible from Kotlin reflect. Therefore, we use the Java reflection API to
      // construct default instances.
      effectClass.java.constructors.first { it.parameterCount == 0 }.newInstance()
    }
    return StateEffect(nextState, effect)
  }
}

class ReducerStrategy(
  private val reducer: KClass<out Reducer<out Any, out Any>>
) : TransitionStrategy {
  override fun invoke(currentState: Any, event: Any): ReductionResult {
    val reducerInstance: Reducer<Any, Any> = reducer.primaryConstructor!!.call() as Reducer<Any, Any>
    return reducerInstance.reduce(currentState, event)
  }
}

fun debug(message: String) {
  println(message)
}

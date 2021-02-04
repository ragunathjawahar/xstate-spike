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
    val effect = createClassInstance(effectClass)
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

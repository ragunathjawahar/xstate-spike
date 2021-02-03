package xstate.visitors.mobius

sealed class ReductionResult { // TODO: 03/02/21 Add parameterized type
  data class StateEffect(
    val state: Any,
    val effect: Any
  ) : ReductionResult()

  // TODO
  // 1. StateEffects
  // 2. Effect
  // 3. Effects
}

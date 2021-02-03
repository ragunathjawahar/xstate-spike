package xstate.visitors.mobius

sealed class ReductionResult {
  companion object {
    val NoOpReducer: (currentState: Any, event: Any) -> ReductionResult =
      { _, _ -> TODO() }
  }

  data class StateEffect(
    val state: Any,
    val effect: Any
  ) : ReductionResult()

  // TODO
  // 1. StateEffects
  // 2. Effect
  // 3. Effects
}

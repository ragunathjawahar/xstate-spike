package xstate.visitors.mobius

interface Reducer<S : Any, E : Any> {
  companion object {
    object NoOpReducer : Reducer<Nothing, Nothing> {
      override fun reduce(currentState: Nothing, event: Nothing): ReductionResult {
        throw IllegalAccessError("`NoOpReducer` is a sentinel placeholder and should never be invoked.")
      }
    }
  }

  fun reduce(currentState: S, event: E): ReductionResult
}

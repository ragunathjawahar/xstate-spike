package io.redgreen.kstate.contract

interface Reducer<S, E> {
  fun reduce(currentState: S, event: E): S
}

object UseGeneratedReducer : Reducer<Nothing, Nothing> {
  private val simpleClassName = UseGeneratedReducer::class.simpleName

  override fun reduce(currentState: Nothing, event: Nothing): Nothing {
    throw IllegalAccessError("$simpleClassName is a sentinel placeholder and it should never be invoked.")
  }
}

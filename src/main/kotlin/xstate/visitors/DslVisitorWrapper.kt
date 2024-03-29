package xstate.visitors

import kotlin.reflect.KClass
import xstate.DslVisitor
import xstate.visitors.mobius.Reducer

class DslVisitorWrapper(
  internal var visitor: DslVisitor? = null
) : DslVisitor {
  override fun onName(name: String) {
    visitor?.onName(name)
  }

  override fun onInitialState(
    initialState: KClass<out Any>,
    effects: Set<KClass<out Any>>
  ) {
    visitor?.onInitialState(initialState, effects)
  }

  override fun onState(
    state: KClass<out Any>,
    final: Boolean
  ) {
    visitor?.onState(state, final)
  }

  override fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>,
    reducer: KClass<out Reducer<out Any, out Any>>
  ) {
    visitor?.onTransition(event, next, effects, reducer)
  }
}

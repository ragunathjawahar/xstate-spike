package xstate

import kotlin.reflect.KClass
import xstate.visitors.mobius.Reducer

interface DslVisitor {
  fun onName(name: String)

  fun onInitialState(
    initialState: KClass<out Any>,
    effects: Set<KClass<out Any>>
  )

  fun onState(
    state: KClass<out Any>,
    final: Boolean
  )

  fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>,
    reducer: KClass<out Reducer<out Any, out Any>>
  )
}

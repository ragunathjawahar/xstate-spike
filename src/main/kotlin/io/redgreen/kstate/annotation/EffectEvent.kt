package io.redgreen.kstate.annotation

import io.redgreen.kstate.contract.Effect
import kotlin.reflect.KClass

annotation class EffectEvent(
  vararg val effects: KClass<out Effect>
)

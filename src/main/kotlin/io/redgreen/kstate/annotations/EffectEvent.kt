package io.redgreen.kstate.annotations

import io.redgreen.kstate.Effect
import kotlin.reflect.KClass

annotation class EffectEvent(
  vararg val effects: KClass<out Effect>
)

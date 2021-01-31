package io.redgreen.kstate.annotations

import io.redgreen.kstate.Effect
import io.redgreen.kstate.State
import kotlin.reflect.KClass

annotation class Next(
  val state: KClass<out State>,
  vararg val effects: KClass<out Effect>
)

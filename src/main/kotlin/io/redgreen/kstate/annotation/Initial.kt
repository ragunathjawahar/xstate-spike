package io.redgreen.kstate.annotation

import io.redgreen.kstate.contract.Effect
import io.redgreen.kstate.contract.State
import kotlin.reflect.KClass

annotation class Initial(
  val state: KClass<out State>,
  vararg val effects: KClass<out Effect>
)

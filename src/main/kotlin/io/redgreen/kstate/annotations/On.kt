package io.redgreen.kstate.annotations

import io.redgreen.kstate.Event
import io.redgreen.kstate.State
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.reflect.KClass

@Repeatable
@Retention(SOURCE)
annotation class On(
  val event: KClass<out Event>,
  val goto: KClass<out State>,
)

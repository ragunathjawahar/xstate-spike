package io.redgreen.kstate.annotations

import io.redgreen.kstate.Event
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.reflect.KClass

@Repeatable
@Retention(SOURCE)
annotation class On(
  val event: KClass<out Event>,
  val next: Next,
)

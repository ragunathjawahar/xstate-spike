package io.redgreen.kstate.annotation

import io.redgreen.kstate.contract.Event
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.reflect.KClass

@Repeatable
@Retention(SOURCE)
annotation class On(
  val event: KClass<out Event>,
  val next: Next,
)

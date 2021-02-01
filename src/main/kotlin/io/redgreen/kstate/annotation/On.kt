package io.redgreen.kstate.annotation

import io.redgreen.kstate.contract.Event
import io.redgreen.kstate.contract.UseGeneratedReducer
import io.redgreen.kstate.contract.Reducer
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.reflect.KClass

@Repeatable
@Retention(SOURCE)
annotation class On(
  val event: KClass<out Event>,
  val next: Next,
  val reducer: KClass<out Reducer<*, *>> = UseGeneratedReducer::class
)

package xstate

import kotlin.LazyThreadSafetyMode.NONE

fun <T> fastLazy(initializer: () -> T): Lazy<T> =
  lazy(NONE, initializer)

fun <S : Any, E : Any, F : Any, V: DslVisitor> runVisitor(
  machine: Machine<S, E, F>,
  dslVisitor: V
): V =
  machine.visit(dslVisitor)

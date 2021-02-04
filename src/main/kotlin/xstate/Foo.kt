package xstate

fun <S : Any, E : Any, F : Any, V: DslVisitor> runVisitor(
  machine: Machine<S, E, F>,
  dslVisitor: V
): V =
  machine.visit(dslVisitor)

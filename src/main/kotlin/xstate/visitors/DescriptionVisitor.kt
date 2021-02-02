package xstate.visitors

import kotlin.reflect.KClass
import xstate.DslVisitor

class DescriptionVisitor : DslVisitor {
  private val summaryBuilder = StringBuilder()

  val description: String
    get() = summaryBuilder.toString()

  override fun onName(name: String) {
    val title = "States & transitions ($name)"
    val separator = title.chars().toArray().map { "=" }.toList().joinToString("")

    summaryBuilder
      .append(separator).append("\n")
      .append(title).append("\n")
      .append(separator).append("\n")
  }

  override fun onInitialState(initialState: KClass<out Any>) {
    summaryBuilder
      .append("Initial: ${initialState.simpleName}")
      .append("\n")
  }

  override fun onState(state: KClass<out Any>) {
    summaryBuilder
      .append("=> ${state.java.simpleName}")
      .append("\n")
  }

  override fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>
  ) {
    summaryBuilder
      .append("    on: ")
      .append(event.simpleName)
      .append(" -> ")
      .append(next.simpleName)
      .append("\n")
  }
}

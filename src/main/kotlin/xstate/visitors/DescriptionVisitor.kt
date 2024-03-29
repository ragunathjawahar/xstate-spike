package xstate.visitors

import kotlin.reflect.KClass
import xstate.DslVisitor
import xstate.visitors.mobius.Reducer

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

  override fun onInitialState(
    initialState: KClass<out Any>,
    effects: Set<KClass<out Any>>
  ) {
    summaryBuilder
      .append("Initial: ${initialState.simpleName}")
      .append(", effects: [${effects.map { it.simpleName }.joinToString()}]")
      .append("\n")
  }

  override fun onState(
    state: KClass<out Any>,
    final: Boolean
  ) {
    summaryBuilder
      .append("=> ${state.java.simpleName}")
      .apply {
        if (final) {
          append(" (final)")
        }
      }
      .append("\n")
  }

  override fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>,
    reducer: KClass<out Reducer<out Any, out Any>>
  ) {
    summaryBuilder
      .append("    on: ")
      .append(event.simpleName)
      .append(" -> ")
      .append(next.simpleName)
      .append(", effects: [${effects.map { it.simpleName }.joinToString()}]")
      .append("\n")
  }
}

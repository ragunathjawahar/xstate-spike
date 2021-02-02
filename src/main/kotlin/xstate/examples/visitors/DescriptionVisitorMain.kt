package xstate.examples.visitors

import xstate.examples.trafficlights.runTrafficLightsVisitor
import xstate.visitors.DescriptionVisitor

fun main() {
  val visitor = runTrafficLightsVisitor(DescriptionVisitor()) as DescriptionVisitor
  println(visitor.description)
}

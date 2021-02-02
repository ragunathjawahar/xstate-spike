package xstate.examples.visitors

import xstate.examples.trafficlights.runTrafficLightsVisitor
import xstate.visitors.XStateJsonVisitor

fun main() {
  val visitor = runTrafficLightsVisitor(XStateJsonVisitor()) as XStateJsonVisitor
  println(visitor.json)
}

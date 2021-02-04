package xstate.examples.visitors

import xstate.examples.trafficlights.trafficLightsStateMachine
import xstate.runVisitor
import xstate.visitors.DescriptionVisitor

fun main() {
  val visitor = runVisitor(trafficLightsStateMachine, DescriptionVisitor())
  println(visitor.description)
}

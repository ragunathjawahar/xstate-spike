package xstate.examples.visitors

import xstate.examples.trafficlights.trafficLightsStateMachine
import xstate.runVisitor
import xstate.visitors.XStateJsonVisitor

fun main() {
  val visitor = runVisitor(trafficLightsStateMachine, XStateJsonVisitor())
  println(visitor.json)
}

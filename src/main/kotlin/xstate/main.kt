package xstate

import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
import xstate.visitors.MobiusVisitor

fun main() {
  val visitor = MobiusVisitor()

  Machine<TrafficLightsState, TrafficLightsEvent, TrafficLightsEffect>(visitor, "Traffic Lights", Green::class) {
    states {
      state(Green::class) {
        on(CountDownElapsed::class, next = Yellow::class)
      }
      state(Yellow::class) {
        on(CountDownElapsed::class, next = Red::class)
      }
      state(Red::class) {
        on(CountDownElapsed::class, next = Green::class)
      }
    }
  }()

  println("Initial state:" + visitor.initialState)
  println("Green + CountDownElapsed = " + visitor.updateFunction.invoke(Green, CountDownElapsed))
  println("Yellow + CountDownElapsed = " + visitor.updateFunction.invoke(Yellow, CountDownElapsed))
  println("Red + CountDownElapsed = " + visitor.updateFunction.invoke(Red, CountDownElapsed))
}

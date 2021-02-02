package xstate

import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
import xstate.hooks.DescriptionHook

fun main() {
  val hook = DescriptionHook()

  Machine<TrafficLightsState, TrafficLightsEvent, TrafficLightsEffect>("Traffic Lights", Green::class, hook) {
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

  println(hook.description)
}

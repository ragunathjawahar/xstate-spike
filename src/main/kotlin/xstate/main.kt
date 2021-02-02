package xstate

import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
import xstate.hooks.XStateJsonHook

fun main() {
  val hook = XStateJsonHook()

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

  println(hook.json)
}

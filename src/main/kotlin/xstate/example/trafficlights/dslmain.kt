package xstate.example.trafficlights

import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEffect.BeginCountDown
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
import xstate.DslVisitor
import xstate.Machine

fun runTrafficLightsVisitor(
  visitor: DslVisitor
): DslVisitor {
  Machine
    .create<TrafficLightsState, TrafficLightsEvent, TrafficLightsEffect>(visitor, "Traffic Lights", Green::class) {
      states {
        state(Green::class) {
          on(CountDownElapsed::class, next = Yellow::class, effects = setOf(BeginCountDown::class))
        }
        state(Yellow::class) {
          on(CountDownElapsed::class, next = Red::class, effects = setOf(BeginCountDown::class))
        }
        state(Red::class) {
          on(CountDownElapsed::class, next = Green::class, effects = setOf(BeginCountDown::class))
        }
      }
    }

  return visitor
}

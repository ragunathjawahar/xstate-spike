package xstate.examples.trafficlights

import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEffect.BeginCountDown
import trafficlights.TrafficLightsEffect.BeginCountDown.Companion.YELLOW_TO_RED_DURATION
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
import xstate.Machine
import xstate.visitors.mobius.Reducer
import xstate.visitors.mobius.ReductionResult
import xstate.visitors.mobius.ReductionResult.StateEffect

val trafficLightsStateMachine = Machine
  .create<TrafficLightsState, TrafficLightsEvent, TrafficLightsEffect>("Traffic Lights") {
    initial(Green::class)

    state(Green::class) {
      on(
        CountDownElapsed::class,
        next = Yellow::class,
        effects = setOf(BeginCountDown::class),
        reducer = GreenToYellowReducer::class
      )
    }

    state(Yellow::class) {
      // Despite having a reducer, we still have to specify the state class. We also have to figure out
      // how to keep the next state, effects, and the reducer in sync.
      on(CountDownElapsed::class, next = Red::class, effects = setOf(BeginCountDown::class))
    }

    state(Red::class) {
      on(CountDownElapsed::class, next = Green::class, effects = setOf(BeginCountDown::class))
    }
  }

// TODO: 03/02/21 Okay to have object or class as a reducer?
class GreenToYellowReducer : Reducer<Green, CountDownElapsed> {
  override fun reduce(currentState: Green, event: CountDownElapsed): ReductionResult =
    StateEffect(Yellow, BeginCountDown(YELLOW_TO_RED_DURATION))
}

package trafficlights

import io.redgreen.kstate.annotation.EffectEvent
import io.redgreen.kstate.annotation.Initial
import io.redgreen.kstate.annotation.Next
import io.redgreen.kstate.annotation.On
import io.redgreen.kstate.annotation.StateMachine
import io.redgreen.kstate.contract.Effect
import io.redgreen.kstate.contract.Event
import io.redgreen.kstate.contract.State
import trafficlights.TrafficLightsEffect.BeginCountDown
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState.Green

@StateMachine
@Initial(Green::class, effects = [BeginCountDown::class])
class TrafficLightsStateMachine

sealed class TrafficLightsState : State {
  @On(
    CountDownElapsed::class,
    Next(Green::class, effects = [BeginCountDown::class])
  )
  object Red : TrafficLightsState() {
    override fun toString(): String = this::class.java.simpleName
  }

  @On(
    CountDownElapsed::class,
    Next(Red::class, effects = [BeginCountDown::class])
  )
  object Yellow : TrafficLightsState() {
    override fun toString(): String = this::class.java.simpleName
  }

  @On(
    CountDownElapsed::class,
    Next(Yellow::class, effects = [BeginCountDown::class])
  )
  object Green : TrafficLightsState() {
    override fun toString(): String = this::class.java.simpleName
  }
}

sealed class TrafficLightsEvent : Event {
  @EffectEvent(BeginCountDown::class)
  object CountDownElapsed : TrafficLightsEvent()
}

sealed class TrafficLightsEffect : Effect {
  object BeginCountDown : TrafficLightsEffect()
}

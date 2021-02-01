package counter

import counter.CounterEvent.Decrement
import counter.CounterEvent.Increment
import io.redgreen.kstate.annotation.Initial
import io.redgreen.kstate.annotation.Next
import io.redgreen.kstate.annotation.On
import io.redgreen.kstate.annotation.StateMachine
import io.redgreen.kstate.contract.Event
import io.redgreen.kstate.contract.Reducer
import io.redgreen.kstate.contract.State

@StateMachine
@Initial(state = CounterState::class)
class CounterStateMachine

@On(Increment::class, Next(CounterState::class), IncrementReducer::class)
@On(Decrement::class, Next(CounterState::class), DecrementReducer::class)
data class CounterState(
  val value: Int
) : State

sealed class CounterEvent : Event {
  object Increment : CounterEvent()
  object Decrement : CounterEvent()
}

class IncrementReducer : Reducer<CounterState, Increment> {
  override fun reduce(currentState: CounterState, event: Increment): CounterState {
    return CounterState(currentState.value + 1)
  }
}

class DecrementReducer : Reducer<CounterState, Increment> {
  override fun reduce(currentState: CounterState, event: Increment): CounterState {
    return CounterState(currentState.value - 1)
  }
}

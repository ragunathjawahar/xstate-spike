package xstate.examples.trafficlights

import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
import xstate.mobius.view.ViewRenderer

class TrafficLightsViewRenderer(
  private val view: TrafficLightsView
) : ViewRenderer<TrafficLightsState> {
  override fun render(model: TrafficLightsState) {
    view.clear()
    when (model) {
      Red -> view.showRed()
      Yellow -> view.showYellow()
      Green -> view.showGreen()
    }
  }
}

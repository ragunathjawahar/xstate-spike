package xstate.examples.trafficlights

import com.spotify.mobius.First
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import java.awt.Color
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel
import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEffect.BeginCountDown
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsState
import xstate.fastLazy
import xstate.mobius.MobiusDelegate
import xstate.runVisitor
import xstate.visitors.mobius.MobiusVisitor
import xstate.visitors.mobius.ReductionResult.StateEffect

fun main() {
  val trafficLightsPanel = TrafficLightsPanel()
  JFrame().apply {
    title = "Traffic Lights"
    pack()
    setSize(200, 600)
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    setLocationRelativeTo(null)
    contentPane = trafficLightsPanel
    isVisible = true
  }

  trafficLightsPanel.start()
}

class TrafficLightsPanel : JPanel(), TrafficLightsView {
  private val redPanel = JPanel()
  private val yellowPanel = JPanel()
  private val greenPanel = JPanel()

  private val lights = listOf(redPanel, yellowPanel, greenPanel)

  init {
    layout = BoxLayout(this, BoxLayout.Y_AXIS)
    add(redPanel)
    add(yellowPanel)
    add(greenPanel)
  }

  private val visitor by fastLazy { runVisitor(trafficLightsStateMachine, MobiusVisitor()) }

  private val mobiusDelegate by fastLazy {
    MobiusDelegate(
      visitor.initialState as TrafficLightsState,
      {
        // TODO: 04/02/21 Add support for init
        First.first(it, setOf(BeginCountDown()))
      },
      mobiusUpdateFunction(),
      TrafficLightsEffectHandler.create(),
      TrafficLightsViewRenderer(this)
    )
  }

  private fun mobiusUpdateFunction() =
    Update<TrafficLightsState, TrafficLightsEvent, TrafficLightsEffect> { currentState, event ->
      val (nextState, effect) = visitor.updateFunction.invoke(currentState, event) as StateEffect
      Next.next(nextState as TrafficLightsState, setOf(effect as TrafficLightsEffect))
    }

  override fun clear() {
    lights.onEach { it.background = Color.LIGHT_GRAY }
  }

  override fun showRed() {
    redPanel.background = Color.RED
  }

  override fun showYellow() {
    yellowPanel.background = Color.YELLOW
  }

  override fun showGreen() {
    greenPanel.background = Color.GREEN
  }

  /// -- Framework Code -- ///

  fun start() {
    mobiusDelegate.start()
  }
}

package xstate.examples.trafficlights

import java.awt.Color
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel
import trafficlights.TrafficLightsEffect.BeginCountDown
import trafficlights.TrafficLightsEffect.BeginCountDown.Companion.DEFAULT_DURATION
import trafficlights.TrafficLightsEvent.CountDownElapsed
import trafficlights.TrafficLightsState
import trafficlights.TrafficLightsState.Green
import trafficlights.TrafficLightsState.Red
import trafficlights.TrafficLightsState.Yellow
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

  override fun showRed() {
    redPanel.background = Color.RED
  }

  override fun showYellow() {
    yellowPanel.background = Color.YELLOW
  }

  override fun showGreen() {
    greenPanel.background = Color.GREEN
  }

  private fun clear() {
    lights.onEach { it.background = Color.LIGHT_GRAY }
  }

  private fun render(state: TrafficLightsState) {
    clear()
    when (state) {
      Red -> showRed()
      Yellow -> showYellow()
      Green -> showGreen()
    }
  }

  /// -- Framework Code -- ///

  fun start() {
    val trafficLightsVisitor = runTrafficLightsVisitor(MobiusVisitor()) as MobiusVisitor
    currentState = trafficLightsVisitor.initialState as TrafficLightsState
    render(currentState)
    val delay = DEFAULT_DURATION
    Thread.sleep(delay)

    while (true) {
      val (state, effect) = trafficLightsVisitor.updateFunction.invoke(currentState, CountDownElapsed) as StateEffect
      Thread.sleep((effect as BeginCountDown).duration)
      currentState = state as TrafficLightsState
      render(currentState)
    }
  }

  private lateinit var currentState: TrafficLightsState
}

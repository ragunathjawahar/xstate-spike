package xstate.visitors

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import xstate.examples.matter.matterDsl
import xstate.examples.trafficlights.trafficLightsStateMachine
import xstate.runVisitor

internal class XStateJsonVisitorTest {
  private val visitor = XStateJsonVisitor()

  @Test
  fun `traffic lights`() {
    Approvals
      .verify(runVisitor(trafficLightsStateMachine, visitor).json)
  }

  @Test
  fun matter() {
    Approvals
      .verify(runVisitor(matterDsl, visitor).json)
  }
}

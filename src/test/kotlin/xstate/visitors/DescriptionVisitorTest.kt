package xstate.visitors

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import xstate.examples.matter.matterDsl
import xstate.examples.trafficlights.trafficLightsStateMachine
import xstate.runVisitor

internal class DescriptionVisitorTest {
  private val visitor = DescriptionVisitor()

  @Test
  fun `traffic lights`() {
    Approvals
      .verify(runVisitor(trafficLightsStateMachine, visitor).description)
  }

  @Test
  fun matter() {
    Approvals
      .verify(runVisitor(matterDsl, visitor).description)
  }
}

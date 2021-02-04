package xstate.visitors

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import xstate.examples.trafficlights.trafficLightsStateMachine
import xstate.runVisitor

internal class DescriptionVisitorTest {
  @Test
  fun `traffic lights`() {
    Approvals
      .verify(runVisitor(trafficLightsStateMachine, DescriptionVisitor()).description)
  }
}

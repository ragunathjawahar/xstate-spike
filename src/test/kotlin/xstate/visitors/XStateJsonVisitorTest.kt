package xstate.visitors

import org.approvaltests.Approvals
import org.junit.jupiter.api.Test
import xstate.examples.trafficlights.runTrafficLightsVisitor

internal class XStateJsonVisitorTest {
  private val visitor = XStateJsonVisitor()

  @Test
  fun `traffic lights`() {
    runTrafficLightsVisitor(visitor)
    Approvals.verify(visitor.json)
  }
}

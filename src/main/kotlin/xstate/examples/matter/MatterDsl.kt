package xstate.examples.matter

import xstate.Machine
import xstate.examples.matter.MatterEvent.Condense
import xstate.examples.matter.MatterEvent.Freeze
import xstate.examples.matter.MatterEvent.Melt
import xstate.examples.matter.MatterEvent.Vaporize
import xstate.examples.matter.MatterState.Gas
import xstate.examples.matter.MatterState.Liquid
import xstate.examples.matter.MatterState.Solid
import xstate.runVisitor
import xstate.visitors.XStateJsonVisitor

fun main() {
  println(runVisitor(matterDsl, XStateJsonVisitor()).json)
}

val matterDsl = Machine.create<MatterState, MatterEvent, Nothing>("Matter") {
  states {
    initial(Solid::class)

    state(Solid::class) {
      on(Melt::class, next = Liquid::class)
    }

    state(Liquid::class) {
      on(Vaporize::class, next = Gas::class)
      on(Freeze::class, next = Solid::class)
    }

    state(Gas::class) {
      on(Condense::class, next = Liquid::class)
    }
  }
}

sealed class MatterState {
  object Solid : MatterState()
  object Liquid : MatterState()
  object Gas : MatterState()
}

sealed class MatterEvent {
  object Melt : MatterEvent()
  object Vaporize : MatterEvent()
  object Condense : MatterEvent()
  object Freeze : MatterEvent()
}

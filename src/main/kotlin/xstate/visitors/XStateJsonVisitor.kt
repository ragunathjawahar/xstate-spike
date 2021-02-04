package xstate.visitors // TODO: 04/02/21 Move to `mobius.visitor` package

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import kotlin.reflect.KClass
import xstate.DslVisitor
import xstate.visitors.mobius.Reducer

private typealias EventName = String
private typealias StateName = String
private typealias Transition = Pair<EventName, StateName>

class XStateJsonVisitor : DslVisitor {
  companion object {
    private const val KEY_ID = "id"
    private const val KEY_INITIAL = "initial"
    private const val KEY_STATES = "states"
    private const val KEY_ON = "on"

    private const val XSTATE_VIZ_LINK = "https://xstate.js.org/viz/"
    private const val XSTATE_NOTE = "\n\n~ NOTE: Link to the viz tool: $XSTATE_VIZ_LINK ~"
  }

  val json: String
    get() {
      val machineJsonObject = buildXStateJsonObject()
      val gson = GsonBuilder().setPrettyPrinting().create()
      return gson.toJson(machineJsonObject) + XSTATE_NOTE
    }

  private lateinit var machineName: String
  private lateinit var initialStateName: StateName
  private lateinit var currentStateClass: KClass<out Any>
  private val statesAndTransitions = linkedMapOf<StateName, MutableList<Transition>>()

  override fun onName(name: String) {
    machineName = name
  }

  override fun onInitialState(
    initialState: KClass<out Any>,
    effects: Set<KClass<out Any>>
  ) {
    initialStateName = initialState.simpleName!!
  }

  override fun onState(state: KClass<out Any>) {
    currentStateClass = state
    statesAndTransitions[state.simpleName]
  }

  override fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>,
    reducer: KClass<out Reducer<out Any, out Any>>
  ) {
    val containsTransitionsForState = statesAndTransitions.containsKey(currentStateClass.simpleName)
    val transition = Transition(event.simpleName!!, next.simpleName!!)

    if (containsTransitionsForState) {
      statesAndTransitions[currentStateClass.simpleName]!!.add(transition)
    } else {
      statesAndTransitions[currentStateClass.simpleName!!] = mutableListOf(transition)
    }
  }

  private fun buildXStateJsonObject(): JsonObject {
    val statesObject = JsonObject()
    val machineJsonObject = JsonObject().apply {
      add(KEY_ID, JsonPrimitive(machineName))
      add(KEY_INITIAL, JsonPrimitive(initialStateName))
      add(KEY_STATES, statesObject)
    }

    statesAndTransitions
      .entries
      .onEach { (stateName, transitions) ->
        val transitionsJsonObject = JsonObject()

        transitions
          .onEach { (eventName, nextStateName) -> transitionsJsonObject.add(eventName, JsonPrimitive(nextStateName)) }

        val onJsonObject = JsonObject().apply {
          add(KEY_ON, transitionsJsonObject)
        }

        statesObject.add(stateName, onJsonObject)
      }

    return machineJsonObject
  }
}

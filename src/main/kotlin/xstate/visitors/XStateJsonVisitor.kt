package xstate.visitors

import kotlin.reflect.KClass
import org.json.JSONObject
import xstate.DslVisitor
import xstate.visitors.mobius.Reducer

class XStateJsonVisitor : DslVisitor {
  companion object {
    private const val KEY_ID = "id"
    private const val KEY_INITIAL = "initial"
    private const val KEY_STATES = "states"
    private const val KEY_ON = "on"
  }

  private val machineJsonObject = JSONObject()
  private var currentStateJsonObject: JSONObject? = null

  val json: String
    get() = machineJsonObject.toString(2) + "\n\n~ NOTE: Link to the viz tool: https://xstate.js.org/viz/ ~"

  override fun onName(name: String) {
    machineJsonObject.put(KEY_ID, name) // TODO: 04/02/21 Extract constants and put them in one place
  }

  override fun onInitialState(
    initialState: KClass<out Any>,
    effects: Set<KClass<out Any>>
  ) {
    machineJsonObject.put(KEY_INITIAL, initialState.simpleName)
  }

  override fun onState(state: KClass<out Any>) {
    currentStateJsonObject = JSONObject()

    if (!machineJsonObject.has(KEY_STATES)) {
      val statesJsonObject = JSONObject()
      machineJsonObject.put(KEY_STATES, statesJsonObject)
      statesJsonObject.put(state.simpleName, currentStateJsonObject)
    } else {
      val statesJsonObject = machineJsonObject.get(KEY_STATES) as JSONObject
      statesJsonObject.put(state.simpleName, currentStateJsonObject)
    }
  }

  override fun onTransition(
    event: KClass<out Any>,
    next: KClass<out Any>,
    effects: Set<KClass<out Any>>,
    reducer: KClass<out Reducer<out Any, out Any>>
  ) {
    val containsOn = currentStateJsonObject!!.has(KEY_ON)
    if (!containsOn) {
      val onJsonObject = JSONObject().apply {
        put(event.simpleName, next.simpleName)
      }
      currentStateJsonObject!!.put(KEY_ON, onJsonObject)
    } else {
      (currentStateJsonObject!!.get(KEY_ON) as JSONObject).put(event.simpleName, next.simpleName)
    }
  }
}

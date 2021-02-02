package xstate.visitors

import kotlin.reflect.KClass
import org.json.JSONObject
import xstate.DslVisitor

class XStateJsonVisitor : DslVisitor {
  private val machineJsonObject = JSONObject()
  private var currentStateJsonObject: JSONObject? = null

  val json: String
    get() = machineJsonObject.toString(2)

  override fun onName(name: String) {
    machineJsonObject.put("id", name)
  }

  override fun onInitialState(initialState: KClass<out Any>) {
    machineJsonObject.put("initial", initialState.simpleName)
  }

  override fun onState(state: KClass<out Any>) {
    currentStateJsonObject = JSONObject()

    if (!machineJsonObject.has("states")) {
      val statesJsonObject = JSONObject()
      machineJsonObject.put("states", statesJsonObject)
      statesJsonObject.put(state.simpleName, currentStateJsonObject)
    } else {
      val statesJsonObject = machineJsonObject.get("states") as JSONObject
      statesJsonObject.put(state.simpleName, currentStateJsonObject)
    }
  }

  override fun onTransition(event: KClass<out Any>, next: KClass<out Any>) {
    val onJsonObject = JSONObject().apply {
      put(event.simpleName, next.simpleName)
    }
    currentStateJsonObject!!.put("on", onJsonObject)
  }
}

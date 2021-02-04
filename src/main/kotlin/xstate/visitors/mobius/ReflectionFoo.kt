package xstate.visitors.mobius

import kotlin.reflect.KClass
import log.debug

fun createClassInstance(classInstance: KClass<out Any>): Any {
  return if (classInstance.objectInstance != null) {
    debug("${classInstance.simpleName} is a Kotlin object, acquiring instance.")
    classInstance.objectInstance!!
  } else {
    debug("${classInstance.simpleName} is a class, attempting to construct a new object.")
    // Default parameters are not visible from Kotlin reflect. Therefore, we use the Java reflection API to
    // construct default instances.
    classInstance.java.constructors.find { it.parameterCount == 0 }!!.newInstance()
  }
}

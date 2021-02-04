package log

private const val debug = false

fun debug(message: String) {
  if (!debug) return
  println(message)
}

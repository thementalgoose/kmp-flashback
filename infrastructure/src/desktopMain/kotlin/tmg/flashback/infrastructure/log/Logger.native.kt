package tmg.flashback.infrastructure.log

actual fun logInfo(tag: String, msg: String) {
    println("I: $tag/ $msg")
}

actual fun logDebug(tag: String, msg: String) {
    println("D: $tag/ $msg")
}
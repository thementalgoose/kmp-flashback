package tmg.flashback.infrastructure.log

import tmg.flashback.infrastructure.debug.isDebug

fun logException(throwable: Throwable) {
    if (isDebug()) {
        throwable.printStackTrace()
    }
}

expect fun logDebug(tag: String, msg: String)
fun logDebug(msg: String) {
    logDebug("Flashback", msg)
}

expect fun logInfo(tag: String, msg: String)
fun logInfo(msg: String) {
    logInfo("Flashback", msg)
}

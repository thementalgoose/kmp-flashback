package tmg.flashback.infrastructure.log

import android.util.Log
import tmg.flashback.infrastructure.debug.isDebug

actual fun logDebug(tag: String, msg: String) {
    if (isDebug()) {
        Log.d(tag, msg)
    }
}

actual fun logInfo(tag: String, msg: String) {
    Log.i(tag, msg)
}
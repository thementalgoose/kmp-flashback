package tmg.flashback.infrastructure.log

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import platform.Foundation.NSThread
import platform.darwin.OS_LOG_DEFAULT
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_FAULT
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal
import tmg.flashback.infrastructure.debug.isDebug

@OptIn(ExperimentalForeignApi::class)
actual fun logDebug(tag: String, msg: String) {
    if (isDebug()) {
        _os_log_internal(
            __dso_handle.ptr,
            OS_LOG_DEFAULT,
            OS_LOG_TYPE_INFO,
            "%s",
            message("D", tag, msg)
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun logInfo(tag: String, msg: String) {
    _os_log_internal(
        __dso_handle.ptr,
        OS_LOG_DEFAULT,
        OS_LOG_TYPE_DEFAULT,
        "%s",
        message("I", tag, msg)
    )
}

private fun message(level: String, tag: String, msg: String, t: Throwable? = null): String {
    val str = if (tag.isEmpty()) "$level: $msg" else "$level/$tag: $msg"
    return if (t == null) str else "$str $t"
}
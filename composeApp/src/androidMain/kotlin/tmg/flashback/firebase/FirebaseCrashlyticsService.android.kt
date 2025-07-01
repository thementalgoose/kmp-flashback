package tmg.flashback.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService

internal actual class FirebaseCrashlyticsServiceImpl actual constructor(): FirebaseCrashlyticsService {

    companion object {
        private const val TAG = "Crashlytics"
    }

    private val instance: FirebaseCrashlytics
        get() = FirebaseCrashlytics.getInstance()

    actual override fun setCrashlyticsCollectionEnabled(enabled: Boolean) {
        instance.isCrashlyticsCollectionEnabled = enabled
    }

    actual override fun setCustomKey(key: String, value: String) {
        instance.setCustomKey(key, value)
    }

    actual override fun setCustomKey(key: String, value: Boolean) {
        instance.setCustomKey(key, value)
    }

    actual override fun setUserId(userId: String) {
        instance.setUserId(userId)
    }

    actual override fun logError(msg: String) {
        instance.log(msg)
    }

    actual override fun logInfo(msg: String) {
        instance.log(msg)
    }

    actual override fun recordException(error: Exception) {
        instance.recordException(error)
    }
}
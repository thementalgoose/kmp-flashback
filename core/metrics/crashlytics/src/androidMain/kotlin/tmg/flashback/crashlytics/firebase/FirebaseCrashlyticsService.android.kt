package tmg.flashback.crashlytics.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics

actual class FirebaseCrashlyticsService {

    companion object {
        private const val TAG = "Crashlytics"
    }

    private val instance: FirebaseCrashlytics
        get() = FirebaseCrashlytics.getInstance()

    actual fun setCrashlyticsCollectionEnabled(enabled: Boolean) {
        instance.isCrashlyticsCollectionEnabled = enabled
    }

    actual fun setCustomKey(key: String, value: String) {
        instance.setCustomKey(key, value)
    }

    actual fun setCustomKey(key: String, value: Boolean) {
        instance.setCustomKey(key, value)
    }

    actual fun setUserId(userId: String) {
        instance.setUserId(userId)
    }

    actual fun logError(msg: String) {
        instance.log(msg)
    }

    actual fun logInfo(msg: String) {
        instance.log(msg)
    }

    actual fun recordException(error: Exception) {
        instance.recordException(error)
    }
}
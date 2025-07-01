package tmg.flashback.firebase

import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService
import cocoapods.FirebaseCrashlytics.FIRCrashlytics
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
internal actual class FirebaseCrashlyticsServiceImpl actual constructor(): FirebaseCrashlyticsService {

    companion object {
        private const val TAG = "Crashlytics"
    }

    private val instance: FIRCrashlytics
        get() = FIRCrashlytics.crashlytics()

    actual override fun setCrashlyticsCollectionEnabled(enabled: Boolean) {
        instance.setCrashlyticsCollectionEnabled(enabled)
    }

    actual override fun setCustomKey(key: String, value: String) {
        instance.setCustomValue(value, key)
    }

    actual override fun setCustomKey(key: String, value: Boolean) {
        instance.setCustomValue(value, key)
    }

    actual override fun setUserId(userId: String) {
        instance.setUserID(userId)
    }

    actual override fun logError(msg: String) {
        instance.log(msg)
    }

    actual override fun logInfo(msg: String) {
        instance.log(msg)
    }

    actual override fun recordException(error: Exception) {
        instance.recordError(NSError.errorWithDomain(error.message, 0, emptyMap<Any?, Any>()))
    }
}
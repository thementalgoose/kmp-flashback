package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService

@Single
internal actual class FirebaseCrashlyticsServiceImpl actual constructor(): FirebaseCrashlyticsService {

    companion object {
        private const val TAG = "Crashlytics"
    }

    actual override fun setCrashlyticsCollectionEnabled(enabled: Boolean) { }

    actual override fun setCustomKey(key: String, value: String) { }

    actual override fun setCustomKey(key: String, value: Boolean) { }

    actual override fun setUserId(userId: String) { }

    actual override fun logError(msg: String) { }

    actual override fun logInfo(msg: String) { }

    actual override fun recordException(error: Exception) { }
}
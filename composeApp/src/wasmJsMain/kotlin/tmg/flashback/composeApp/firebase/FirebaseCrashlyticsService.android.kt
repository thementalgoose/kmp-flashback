package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService

@Single(binds = [FirebaseCrashlyticsService::class])
internal actual class FirebaseCrashlyticsServiceImpl actual constructor() : FirebaseCrashlyticsService {
    actual override fun setCrashlyticsCollectionEnabled(enabled: Boolean) { }
    actual override fun setCustomKey(key: String, value: String) { }
    actual override fun setCustomKey(key: String, value: Boolean) { }
    actual override fun setUserId(userId: String) { }
    actual override fun logInfo(msg: String) { }
    actual override fun logError(msg: String) { }
    actual override fun recordException(error: Exception) { }
}
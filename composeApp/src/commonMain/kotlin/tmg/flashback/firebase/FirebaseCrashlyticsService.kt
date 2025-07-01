package tmg.flashback.firebase

import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService

internal expect class FirebaseCrashlyticsServiceImpl(): FirebaseCrashlyticsService {
    override fun setCrashlyticsCollectionEnabled(enabled: Boolean)
    override fun setCustomKey(key: String, value: String)
    override fun setCustomKey(key: String, value: Boolean)
    override fun setUserId(userId: String)
    override fun logInfo(msg: String)
    override fun logError(msg: String)
    override fun recordException(error: Exception)
}
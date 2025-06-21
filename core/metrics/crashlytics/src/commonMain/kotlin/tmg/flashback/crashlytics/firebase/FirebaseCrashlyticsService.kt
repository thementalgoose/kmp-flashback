package tmg.flashback.crashlytics.firebase

expect class FirebaseCrashlyticsService {
    fun setCrashlyticsCollectionEnabled(enabled: Boolean)
    fun setCustomKey(key: String, value: String)
    fun setCustomKey(key: String, value: Boolean)
    fun setUserId(userId: String)
    fun logInfo(msg: String)
    fun logError(msg: String)
    fun recordException(error: Exception)
}
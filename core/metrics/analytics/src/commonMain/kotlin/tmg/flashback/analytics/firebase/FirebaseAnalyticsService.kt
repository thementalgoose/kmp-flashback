package tmg.flashback.analytics.firebase

import kotlin.reflect.KClass

expect class FirebaseAnalyticsService {
    fun setUserId(userId: String)
    fun setAnalyticsCollectionEnabled(enabled: Boolean)
    fun logEvent(event: String, params: Map<String, String> = emptyMap())
    fun setProperty(key: String, value: String)
    fun logViewScreen(screenName: String, params: Map<String, String> = emptyMap(), clazz: KClass<*>? = null)
}
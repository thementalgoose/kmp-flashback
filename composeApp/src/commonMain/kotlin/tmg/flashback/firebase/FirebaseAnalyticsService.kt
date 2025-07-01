package tmg.flashback.firebase

import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import kotlin.reflect.KClass

internal expect class FirebaseAnalyticsServiceImpl(): FirebaseAnalyticsService {
    override fun setUserId(userId: String)
    override fun setAnalyticsCollectionEnabled(enabled: Boolean)
    override fun logEvent(event: String, params: Map<String, String>)
    override fun setProperty(key: String, value: String)
    override fun logViewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?)
}
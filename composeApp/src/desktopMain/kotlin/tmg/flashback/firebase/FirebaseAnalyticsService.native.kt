package tmg.flashback.firebase

import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import kotlin.reflect.KClass

internal actual class FirebaseAnalyticsServiceImpl actual constructor(): FirebaseAnalyticsService {

    actual override fun setUserId(userId: String) { }
    actual override fun setAnalyticsCollectionEnabled(enabled: Boolean) { }
    actual override fun logEvent(event: String, params: Map<String, String>) { }
    actual override fun setProperty(key: String, value: String) { }
    actual override fun logViewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?) { }
}
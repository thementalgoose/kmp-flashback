package tmg.flashback.composeApp.firebase

import org.koin.core.annotation.Single
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService

@Single(binds = [FirebaseAnalyticsService::class])
actual class FirebaseAnalyticsServiceImpl actual constructor() : FirebaseAnalyticsService {
    actual override fun setUserId(userId: String) { }
    actual override fun setAnalyticsCollectionEnabled(enabled: Boolean) { }
    actual override fun logEvent(event: String, params: Map<String, String>) { }
    actual override fun setProperty(key: String, value: String) { }
    actual override fun logViewScreen(screenName: String, params: Map<String, String>, clazz: kotlin.reflect.KClass<*>?) { }
}
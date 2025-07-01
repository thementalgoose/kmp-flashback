package tmg.flashback.firebase

import cocoapods.FirebaseAnalytics.FIRAnalytics
import kotlinx.cinterop.ExperimentalForeignApi
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import kotlin.reflect.KClass

@OptIn(ExperimentalForeignApi::class)
internal actual class FirebaseAnalyticsServiceImpl actual constructor(): FirebaseAnalyticsService {

    actual override fun setUserId(userId: String) {
        FIRAnalytics.setUserID(userId)
    }
    actual override fun setAnalyticsCollectionEnabled(enabled: Boolean) {
        FIRAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
    actual override fun logEvent(event: String, params: Map<String, String>) {
        FIRAnalytics.logEventWithName(event, params.toMap())
    }
    actual override fun setProperty(key: String, value: String) {
        FIRAnalytics.setUserPropertyString(value, forName = key)
    }
    actual override fun logViewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?) {
        FIRAnalytics.logEventWithName("screen_view", parameters = params.toMap())
    }
}
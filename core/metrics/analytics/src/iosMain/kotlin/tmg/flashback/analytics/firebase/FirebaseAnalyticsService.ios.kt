package tmg.flashback.analytics.firebase

import kotlin.reflect.KClass

actual class FirebaseAnalyticsService {
    actual fun setUserId(userId: String) {

    }
    actual fun setAnalyticsCollectionEnabled(enabled: Boolean) {

    }
    actual fun logEvent(event: String, params: Map<String, String>) {

    }
    actual fun setProperty(key: String, value: String) {

    }
    actual fun logViewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?) {

    }
}
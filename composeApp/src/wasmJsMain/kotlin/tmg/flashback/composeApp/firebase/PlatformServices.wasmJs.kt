package tmg.flashback.composeApp.firebase

import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.configuration.firebase.FirebaseRemoteConfigService
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService
import tmg.flashback.notifications.firebase.FirebaseMessagingService

internal actual class FirebaseAnalyticsServiceImpl actual constructor() : FirebaseAnalyticsService {
    actual override fun setUserId(userId: String) { }
    actual override fun setAnalyticsCollectionEnabled(enabled: Boolean) { }
    actual override fun logEvent(event: String, params: Map<String, String>) { }
    actual override fun setProperty(key: String, value: String) { }
    actual override fun logViewScreen(screenName: String, params: Map<String, String>, clazz: kotlin.reflect.KClass<*>?) { }
}

internal actual class FirebaseCrashlyticsServiceImpl actual constructor() : FirebaseCrashlyticsService {
    actual override fun setCrashlyticsCollectionEnabled(enabled: Boolean) { }
    actual override fun setCustomKey(key: String, value: String) { }
    actual override fun setCustomKey(key: String, value: Boolean) { }
    actual override fun setUserId(userId: String) { }
    actual override fun logInfo(msg: String) { }
    actual override fun logError(msg: String) { }
    actual override fun recordException(error: Exception) { }
}

internal actual class FirebaseInstallationServiceImpl actual constructor() : FirebaseInstallationService {
    actual override suspend fun getInstallationId(): String? = null
}

internal actual class FirebaseMessagingServiceImpl actual constructor() : FirebaseMessagingService {
    actual override suspend fun subscribeToTopic(topicId: String): Boolean = false
    actual override suspend fun unsubscribeFromTopic(topicId: String): Boolean = false
}

internal actual class FirebaseRemoteConfigServiceImpl actual constructor() : FirebaseRemoteConfigService {
    actual override suspend fun activate(): Boolean = false
    actual override suspend fun reset() { }
    actual override suspend fun fetch(minimumFetchInterval: Int?) { }
    actual override fun setConfigSettingsAsync(minimumFetchInterval: Int) { }
    actual override fun setDefaultsAsync(defaultValues: Map<String, Any>) { }
    actual override fun getValueString(key: String): String? = null
    actual override fun getValueBoolean(key: String): Boolean = false
}

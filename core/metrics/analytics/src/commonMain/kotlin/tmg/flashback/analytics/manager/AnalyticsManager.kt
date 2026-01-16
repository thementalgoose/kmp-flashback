package tmg.flashback.analytics.manager

import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.infrastructure.log.logDebug
import kotlin.reflect.KClass

interface AnalyticsManager {
    fun logEvent(key: String, params: Map<String, String> = emptyMap())
    fun viewScreen(screenName: String, params: Map<String, String> = emptyMap(), clazz: KClass<*>? = null)
}

internal class AnalyticsManagerImpl(
    private val firebaseAnalyticsService: FirebaseAnalyticsService
): AnalyticsManager {

    override fun logEvent(key: String, params: Map<String, String>) {
        logDebug("Analytics", "logEvent $key - $params")
        firebaseAnalyticsService.logEvent(key, params)
    }

    override fun viewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?) {
        logDebug("Analytics", "viewScreen $screenName - $params")
        firebaseAnalyticsService.logViewScreen(screenName, params, clazz)
    }
}
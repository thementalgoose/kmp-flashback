package tmg.flashback.analytics.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kotlin.reflect.KClass

actual class FirebaseAnalyticsService(
    private val applicationContext: Context
) {
    private val analytics: FirebaseAnalytics
        @SuppressLint("MissingPermission")
        get() = FirebaseAnalytics.getInstance(applicationContext)

    actual fun setUserId(userId: String) {
        analytics.setUserId(userId)
    }
    actual fun setAnalyticsCollectionEnabled(enabled: Boolean) {
        analytics.setAnalyticsCollectionEnabled(enabled)
    }
    actual fun logEvent(event: String, params: Map<String, String>) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                putString(key, value)
            }
        }
        analytics.logEvent(event, bundle)
    }
    actual fun setProperty(key: String, value: String) {
        analytics.setUserProperty(key, value)
    }
    actual fun logViewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            if (clazz != null) {
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, clazz.simpleName)
            }
            for (x in params) {
                putString(x.key, x.value)
            }
        }
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}
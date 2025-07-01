package tmg.flashback.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.java.KoinJavaComponent
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import kotlin.reflect.KClass

internal actual class FirebaseAnalyticsServiceImpl actual constructor(): FirebaseAnalyticsService {

    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    private val analytics: FirebaseAnalytics
        @SuppressLint("MissingPermission")
        get() = FirebaseAnalytics.getInstance(getApplicationContext())

    actual override fun setUserId(userId: String) {
        analytics.setUserId(userId)
    }
    actual override fun setAnalyticsCollectionEnabled(enabled: Boolean) {
        analytics.setAnalyticsCollectionEnabled(enabled)
    }
    actual override fun logEvent(event: String, params: Map<String, String>) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                putString(key, value)
            }
        }
        analytics.logEvent(event, bundle)
    }
    actual override fun setProperty(key: String, value: String) {
        analytics.setUserProperty(key, value)
    }
    actual override fun logViewScreen(screenName: String, params: Map<String, String>, clazz: KClass<*>?) {
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
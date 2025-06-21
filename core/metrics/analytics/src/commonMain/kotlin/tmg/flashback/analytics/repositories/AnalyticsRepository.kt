package tmg.flashback.analytics.repositories

import tmg.flashback.preferences.manager.PreferenceManager

interface AnalyticsRepository {
    var analyticsEnabled: Boolean
}

internal class AnalyticsRepositoryImpl(
    private val preferenceManager: PreferenceManager
): AnalyticsRepository {

    override var analyticsEnabled: Boolean
        get() = preferenceManager.getBoolean(keyAnalytics, true)
        set(value) {
            preferenceManager.save(keyAnalytics, value)
        }

    companion object {
        private const val keyAnalytics: String = "ANALYTICS_OPT_IN"
    }
}
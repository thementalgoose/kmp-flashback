package tmg.flashback.crashlytics.manager

import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService

interface CrashlyticsManager {
    fun logError(msg: String)
    fun log(msg: String)
    fun logException(exception: Exception, msg: String? = null)
}

internal class CrashlyticsManagerImpl(
    private val firebaseCrashlyticsService: FirebaseCrashlyticsService
): CrashlyticsManager {

    override fun logError(msg: String) {
        firebaseCrashlyticsService.logError(msg)
    }

    override fun log(msg: String) {
        firebaseCrashlyticsService.logInfo(msg)
    }

    override fun logException(exception: Exception, msg: String?) {
        firebaseCrashlyticsService.logError(msg ?: exception.message ?: "Error Occured")
        firebaseCrashlyticsService.recordException(exception)
    }
}
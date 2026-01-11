package tmg.flashback.analytics.usecases

import tmg.flashback.analytics.manager.AnalyticsManager

interface LogEventUseCase {
    fun logEvent(key: String, params: Map<String, String> = emptyMap())
}

internal class LogEventUseCaseImpl(
    private val manager: AnalyticsManager
): LogEventUseCase {
    override fun logEvent(
        key: String,
        params: Map<String, String>
    ) {
        manager.logEvent(key, params)
    }
}
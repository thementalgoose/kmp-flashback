package tmg.flashback.analytics.usecases

import org.koin.core.annotation.Single
import tmg.flashback.analytics.manager.AnalyticsManager

interface LogEventUseCase {
    fun logEvent(key: String, params: Map<String, String> = emptyMap())
}

@Single
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
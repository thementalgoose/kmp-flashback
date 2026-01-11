package tmg.flashback.analytics.usecases

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.analytics.manager.AnalyticsManager
import kotlin.test.Test

internal class LogEventUseCaseImplTest {

    private val mockAnalyticsManager: AnalyticsManager = mock(autoUnit)

    private lateinit var underTest: LogEventUseCaseImpl

    private fun initUnderTest() {
        underTest = LogEventUseCaseImpl(
            manager = mockAnalyticsManager
        )
    }

    @Test
    fun `logging event forwards to manager`() {
        val key = "key"
        val params = mapOf("param1" to "x")

        initUnderTest()
        underTest.logEvent(key, params)

        verify {
            mockAnalyticsManager.logEvent(key, params)
        }
    }
}
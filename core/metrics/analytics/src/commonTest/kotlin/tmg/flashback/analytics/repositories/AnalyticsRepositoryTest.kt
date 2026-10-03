package tmg.flashback.analytics.repositories

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class AnalyticsRepositoryTest {

    private lateinit var underTest: AnalyticsRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = AnalyticsRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `save analytics saves udner key`() {
        initUnderTest()
        underTest.analyticsEnabled = true
        verify {
            mockPreferenceManager.save(expectedKeyAnalytics, true)
        }
    }

    @Test
    fun `reading analytics reads value`() {
        initUnderTest()
        every { mockPreferenceManager.getBoolean(expectedKeyAnalytics, true) } returns true

        assertTrue(underTest.analyticsEnabled)
    }

    companion object {
        private const val expectedKeyAnalytics: String = "ANALYTICS_OPT_IN"
    }
}
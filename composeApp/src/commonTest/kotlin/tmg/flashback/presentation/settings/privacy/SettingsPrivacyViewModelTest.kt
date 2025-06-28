package tmg.flashback.presentation.settings.privacy

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.original
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.analytics.repositories.AnalyticsRepository
import tmg.flashback.crashlytics.repositories.CrashlyticsRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsPrivacyViewModelTest {

    private lateinit var underTest: SettingsPrivacyViewModel

    private val mockCrashlyticsRepository: CrashlyticsRepository = mock(autoUnit)
    private val mockAnalyticsRepository: AnalyticsRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsPrivacyViewModel(
            crashlyticsRepository = mockCrashlyticsRepository,
            analyticsRepository = mockAnalyticsRepository,
        )
    }

    @Test
    fun `crashlytics state is populated from repo`() = runTest {
        every { mockAnalyticsRepository.analyticsEnabled } returns false
        every { mockCrashlyticsRepository.crashlyticsEnabled } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().crashReportingEnabled)
        }
    }

    @Test
    fun `analytics state is populated from repo`() = runTest {
        every { mockCrashlyticsRepository.crashlyticsEnabled } returns false
        every { mockAnalyticsRepository.analyticsEnabled } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().analyticsEnabled)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockCrashlyticsRepository.crashlyticsEnabled } returns false
        every { mockAnalyticsRepository.analyticsEnabled } returns false

        initUnderTest()

        underTest.updateCrashlyticsEnabled(true)
        verify {
            mockCrashlyticsRepository.crashlyticsEnabled = true
        }

        underTest.updateAnalyticsEnabled(true)
        verify {
            mockAnalyticsRepository.analyticsEnabled = true
        }
    }
}
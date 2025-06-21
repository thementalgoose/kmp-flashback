package tmg.flashback.crashlytics.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class CrashlyticsRepositoryTest {

    private lateinit var underTest: CrashlyticsRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = CrashlyticsRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `save crashlytics saves udner key`() {
        initUnderTest()
        underTest.crashlyticsEnabled = true
        verify {
            mockPreferenceManager.save(expectedKeyCrashlytics, true)
        }
    }

    @Test
    fun `reading crashlytics reads value`() {
        initUnderTest()
        every { mockPreferenceManager.getBoolean(expectedKeyCrashlytics, true) } returns true

        assertTrue(underTest.crashlyticsEnabled)
    }

    companion object {
        private const val expectedKeyCrashlytics: String = "CRASH_REPORTING"
    }
}
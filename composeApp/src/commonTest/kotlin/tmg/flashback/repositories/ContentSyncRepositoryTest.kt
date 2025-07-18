package tmg.flashback.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ContentSyncRepositoryTest {

    private lateinit var underTest: OnboardingRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = OnboardingRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `saving initial sync saves to repo`() {
        initUnderTest()
        underTest.initialSyncCompleted = true
        verify {
            mockPreferenceManager.save(expectedKeyInitialSync, true)
        }
    }

    @Test
    fun `getting initial sync gets from repo`() {
        every { mockPreferenceManager.getBoolean(expectedKeyInitialSync, false) } returns true
        initUnderTest()
        assertEquals(true, underTest.initialSyncCompleted)
    }

    companion object {
        private const val expectedKeyInitialSync = "REPO_INITIAL_SYNC"
    }
}
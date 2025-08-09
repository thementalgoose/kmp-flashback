package tmg.flashback.feature.highlights.repositories

import dev.mokkery.MockMode
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HighlightsRepositoryImplTest {

    private val mockPreferenceManager: PreferenceManager = mock(autofill)

    private lateinit var underTest: HighlightsRepositoryImpl

    private fun initUnderTest() {
        underTest = HighlightsRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `updating values saves value`() {
        initUnderTest()

        underTest.show = true
        verify {
            mockPreferenceManager.save(expectedKeyRecentHighlights, true)
        }
    }

    @Test
    fun `read value from prefs`() {
        every { mockPreferenceManager.getBoolean(expectedKeyRecentHighlights, false) } returns true

        initUnderTest()

        assertEquals(true, underTest.show)
    }

    companion object {
        private const val expectedKeyRecentHighlights = "RECENT_HIGHLIGHTS"
    }
}
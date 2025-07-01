package tmg.flashback.style.theme

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ThemeManagerTest {

    private lateinit var underTest: ThemeManagerImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = ThemeManagerImpl(
            preferenceManager = mockPreferenceManager
        )
    }

    private val Theme.expectedSaveKey: String
        get() = when (this) {
            Theme.Default -> "default"
            Theme.MaterialYou -> "material_you"
        }


    private val NightMode.expectedSaveKey: String
        get() = when (this) {
            NightMode.DEFAULT -> "AUTO"
            NightMode.DAY -> "DAY"
            NightMode.NIGHT -> "NIGHT"
        }

    @Test
    fun `getting current default theme saves expected key`() {
        initUnderTest()
        every { mockPreferenceManager.getString(EXPECTED_PREFERENCE_THEME) } returns Theme.Default.expectedSaveKey

        assertEquals(Theme.Default, underTest.currentTheme)
    }

    @Test
    fun `getting current material you theme saves expected key`() {
        initUnderTest()
        every { mockPreferenceManager.getString(EXPECTED_PREFERENCE_THEME) } returns Theme.MaterialYou.expectedSaveKey

        assertEquals(Theme.MaterialYou, underTest.currentTheme)
    }

    @Test
    fun `saving current theme saves key`() {
        initUnderTest()
        underTest.currentTheme = Theme.Default

        verify {
            mockPreferenceManager.save(EXPECTED_PREFERENCE_THEME, Theme.Default.expectedSaveKey)
        }
    }

    @Test
    fun `getting current night mode`() {
        initUnderTest()
        NightMode.entries.forEach {
            every { mockPreferenceManager.getString(EXPECTED_PREFERENCE_NIGHT_MODE) } returns it.expectedSaveKey

            assertEquals(it, underTest.currentNightMode)
        }
    }

    @Test
    fun `saving current night mode`() {
        initUnderTest()
        underTest.currentNightMode = NightMode.NIGHT

        verify {
            mockPreferenceManager.save(EXPECTED_PREFERENCE_NIGHT_MODE, NightMode.NIGHT.expectedSaveKey)
        }
    }

    companion object {
        private const val EXPECTED_PREFERENCE_THEME = "THEME_CHOICE"
        private const val EXPECTED_PREFERENCE_NIGHT_MODE = "THEME" // Used to be called theme
    }
}
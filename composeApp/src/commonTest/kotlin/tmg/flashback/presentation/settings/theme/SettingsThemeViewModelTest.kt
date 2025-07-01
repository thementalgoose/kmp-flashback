package tmg.flashback.presentation.settings.theme

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.style.theme.Theme.Default
import tmg.flashback.style.theme.Theme.MaterialYou
import tmg.flashback.style.theme.ThemeManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsThemeViewModelTest {

    private lateinit var underTest: SettingsThemeViewModel

    private val mockThemeManager: ThemeManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsThemeViewModel(
            themeManager = mockThemeManager
        )
    }

    @Test
    fun `theme state is populated from theme manager`() = runTest {
        every { mockThemeManager.currentTheme } returns Default
        initUnderTest()
        underTest.uiState.test {
            assertEquals(Default, awaitItem().theme)
        }
    }

    @Test
    fun `updating theme saves state`() = runTest {
        every { mockThemeManager.currentTheme } returns MaterialYou
        initUnderTest()
        underTest.uiState.test {
            assertEquals(MaterialYou, awaitItem().theme)

            underTest.updateSelection(MaterialYou)

            verify {
                mockThemeManager.currentTheme = MaterialYou
            }
        }
    }
}
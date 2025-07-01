package tmg.flashback.presentation.settings.darkmode

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.style.theme.NightMode.DAY
import tmg.flashback.style.theme.NightMode.NIGHT
import tmg.flashback.style.theme.ThemeManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsDarkModeViewModelTest {

    private lateinit var underTest: SettingsDarkModeViewModel

    private val mockThemeManager: ThemeManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsDarkModeViewModel(
            themeManager = mockThemeManager
        )
    }

    @Test
    fun `dark mode state is populated from theme manager`() = runTest {
        every { mockThemeManager.currentNightMode } returns NIGHT
        initUnderTest()
        underTest.uiState.test {
            assertEquals(NIGHT, awaitItem().nightMode)
        }
    }

    @Test
    fun `updating dark mode saves state`() = runTest {
        every { mockThemeManager.currentNightMode } returns NIGHT
        initUnderTest()
        underTest.uiState.test {
            assertEquals(NIGHT, awaitItem().nightMode)

            underTest.updateSelection(DAY)

            verify {
                mockThemeManager.currentNightMode = DAY
            }
        }
    }
}
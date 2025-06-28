package tmg.flashback.presentation.settings

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.runBlocking
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCase
import tmg.flashback.widget.upnext.usecases.IsWidgetsEnabledUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AllSettingsViewModelTest {

    private lateinit var underTest: AllSettingsViewModel

    private val mockIsWidgetEnabledUseCase: IsWidgetsEnabledUseCase = mock(autoUnit)
    private val mockIsRssEnabledUseCase: IsRssEnabledUseCase = mock(autoUnit)
    private val mockIsInAppBrowserEnabledUseCase: IsInAppBrowserEnabledUseCase = mock(autoUnit)

    private fun initUnderTest() {
        underTest = AllSettingsViewModel(
            isWidgetsEnabledUseCase = mockIsWidgetEnabledUseCase,
            isRssEnabledUseCase = mockIsRssEnabledUseCase,
            isInAppBrowserEnabledUseCase = mockIsInAppBrowserEnabledUseCase
        )
    }

    @Test
    fun `widgets enabled if app supports it`() = runBlocking {
        every { mockIsRssEnabledUseCase.invoke() } returns false
        every { mockIsInAppBrowserEnabledUseCase.invoke() } returns false
        every { mockIsWidgetEnabledUseCase.invoke() } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().isWidgetsSupported)
        }
    }

    @Test
    fun `rss enabled if feature enabled`() = runBlocking {
        every { mockIsWidgetEnabledUseCase.invoke() } returns false
        every { mockIsInAppBrowserEnabledUseCase.invoke() } returns false
        every { mockIsRssEnabledUseCase.invoke() } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().isRssEnabled)
        }
    }

    @Test
    fun `in app browser enabled if feature enabled`() = runBlocking {
        every { mockIsWidgetEnabledUseCase.invoke() } returns false
        every { mockIsRssEnabledUseCase.invoke() } returns false
        every { mockIsInAppBrowserEnabledUseCase.invoke() } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().isInAppBrowserSupported)
        }
    }
}
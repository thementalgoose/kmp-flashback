package tmg.flashback.composeApp.presentation.settings.browser

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.webbrowser.repository.WebRepository
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SettingsBrowserViewModelTest {

    private lateinit var underTest: SettingsBrowserViewModel

    private val mockWebRepository: WebRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = SettingsBrowserViewModel(
            webRepository = mockWebRepository,
        )
    }

    @Test
    fun `enable state is populated from repo`() = runTest {
        every { mockWebRepository.enabled } returns false
        every { mockWebRepository.enableJavascript } returns true
        every { mockWebRepository.toolbarAtTop } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().enableJavascript)
        }
    }

    @Test
    fun `enable javascript state is populated from repo`() = runTest {
        every { mockWebRepository.enableJavascript } returns false
        every { mockWebRepository.enabled } returns true
        every { mockWebRepository.toolbarAtTop } returns false
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().enabled)
        }
    }

    @Test
    fun `toolbar top state is populated from repo`() = runTest {
        every { mockWebRepository.enableJavascript } returns false
        every { mockWebRepository.enabled } returns false
        every { mockWebRepository.toolbarAtTop } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().toolbarAtTop)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockWebRepository.enableJavascript } returns false
        every { mockWebRepository.enabled } returns false
        every { mockWebRepository.toolbarAtTop } returns false

        initUnderTest()

        underTest.updateEnableJavascript(true)
        verify {
            mockWebRepository.enableJavascript = true
        }

        underTest.updateEnabled(true)
        verify {
            mockWebRepository.enabled = true
        }

        underTest.updateToolbarTop(true)
        verify {
            mockWebRepository.toolbarAtTop = true
        }
    }
}
package tmg.flashback.presentation.settings.browser

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
    fun `crashlytics state is populated from repo`() = runTest {
        every { mockWebRepository.enabled } returns false
        every { mockWebRepository.enableJavascript } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().enableJavascript)
        }
    }

    @Test
    fun `analytics state is populated from repo`() = runTest {
        every { mockWebRepository.enableJavascript } returns false
        every { mockWebRepository.enabled } returns true
        initUnderTest()
        underTest.uiState.test {
            assertEquals(true, awaitItem().enabled)
        }
    }

    @Test
    fun `updating values saves values to repo`() = runTest {
        every { mockWebRepository.enableJavascript } returns false
        every { mockWebRepository.enabled } returns false

        initUnderTest()

        underTest.updateEnableJavascript(true)
        verify {
            mockWebRepository.enableJavascript = true
        }

        underTest.updateEnabled(true)
        verify {
            mockWebRepository.enabled = true
        }
    }
}
package tmg.flashback.webbrowser.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import tmg.flashback.webbrowser.repository.WebRepository
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WebViewViewModelTest {

    private val mockWebRepository: WebRepository = mock(autoUnit)

    private lateinit var underTest: WebViewViewModel

    private fun initUnderTest() {
        underTest = WebViewViewModel(
            webRepository = mockWebRepository
        )
    }

    @Test
    fun `toolbar state read from repository`() = runTest {
        every { mockWebRepository.toolbarAtTop } returns true
        initUnderTest()
        underTest.uiState.test {
            assertTrue(awaitItem().toolbarAtTop)
        }
    }
}
package tmg.flashback.webbrowser.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.device.usecases.ShareWebpageUseCase
import tmg.flashback.webbrowser.repository.WebRepository
import kotlin.test.Test
import kotlin.test.assertTrue

internal class WebViewViewModelTest {

    private val mockWebRepository: WebRepository = mock(autoUnit)
    private val mockShareWebpageUseCase: ShareWebpageUseCase = mock(autoUnit)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autoUnit)

    private lateinit var underTest: WebViewViewModel

    private fun initUnderTest() {
        underTest = WebViewViewModel(
            webRepository = mockWebRepository,
            shareWebpageUseCase = mockShareWebpageUseCase,
            openWebpageUseCase = mockOpenWebpageUseCase
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

    @Test
    fun `share calls share use case`() {
        every { mockWebRepository.toolbarAtTop } returns false
        every { mockShareWebpageUseCase.invoke(any()) } returns Unit
        initUnderTest()
        underTest.share("url")
        verify {
            mockShareWebpageUseCase.invoke("url")
        }
    }

    @Test
    fun `open calls open use case`() {
        every { mockWebRepository.toolbarAtTop } returns false
        every { mockOpenWebpageUseCase.invoke(any()) } returns Unit
        initUnderTest()
        underTest.open("url")
        verify {
            mockOpenWebpageUseCase.invoke("url")
        }
    }
}
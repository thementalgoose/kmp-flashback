package tmg.flashback.feature.highlights.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.highlights.domain.GetNewsItemsUseCase
import tmg.flashback.feature.highlights.domain.models.NewsItem
import tmg.flashback.feature.highlights.repositories.HighlightsRepository
import tmg.flashback.infrastructure.datetime.now
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HighlightsViewModelTest {

    private val mockGetNewsItemsUseCase: GetNewsItemsUseCase = mock(autofill)
    private val mockHighlightsRepository: HighlightsRepository = mock(autofill)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autofill)

    private lateinit var underTest: HighlightsViewModel

    private fun initUnderTest() {
        underTest = HighlightsViewModel(
            getNewsItemsUseCase = mockGetNewsItemsUseCase,
            highlightsRepository = mockHighlightsRepository,
            openWebpageUseCase = mockOpenWebpageUseCase,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `refresh when disabled doesnt make request`() {
        every { mockHighlightsRepository.show } returns false

        initUnderTest()
        underTest.refresh()

        verifySuspend(exactly(0)) {
            mockGetNewsItemsUseCase.getNews()
        }
    }

    @Test
    fun `refresh with no news items hides value`() = runTest {
        every { mockHighlightsRepository.show } returns true
        everySuspend { mockGetNewsItemsUseCase.getNews() } returns emptyList()

        initUnderTest()
        underTest.refresh()

        underTest.uiState.test {
            val state = awaitItem()

            assertEquals(emptyList(), state.news)
            assertEquals(true, state.show)
        }
    }

    @Test
    fun `refresh with news items sets value`() = runTest {
        every { mockHighlightsRepository.show } returns true
        everySuspend { mockGetNewsItemsUseCase.getNews() } returns listOf(fakeNewsItem())

        initUnderTest()
        underTest.refresh()

        underTest.uiState.test {
            val state = awaitItem()

            assertEquals(listOf(fakeNewsItem()), state.news)
            assertEquals(true, state.show)
        }
    }

    @Test
    fun `hiding sets show to false`() = runTest {
        every { mockHighlightsRepository.show } returns true

        initUnderTest()
        underTest.hide()

        underTest.uiState.test {
            val state = awaitItem()

            assertEquals(false, state.show)
        }
    }

    @Test
    fun `opening webpage calls use case`() {
        initUnderTest()

        underTest.clickItem("url")
        verify {
            mockOpenWebpageUseCase.invoke("url")
        }
    }

    private fun fakeNewsItem(): NewsItem = NewsItem(
        message = "Some kind of breaking news has happened",
        link = "https://www.google.com",
        image = null,
        date = LocalDate.now()
    )
}
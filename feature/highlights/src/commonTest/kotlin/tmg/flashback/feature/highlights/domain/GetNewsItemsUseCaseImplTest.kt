package tmg.flashback.feature.highlights.domain

import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import tmg.flashback.feature.highlights.domain.models.NewsItem
import tmg.flashback.news.api.FlashbackNewsApi
import tmg.flashback.news.models.MetadataWrapper
import tmg.flashback.news.models.news.News
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetNewsItemsUseCaseImplTest {

    private val mockFlashbackNewsApi: FlashbackNewsApi = mock(autofill)

    private lateinit var underTest: GetNewsItemsUseCaseImpl

    private fun initUnderTest() {
        underTest = GetNewsItemsUseCaseImpl(
            flashbackNewsApi = mockFlashbackNewsApi
        )
    }

    @Test
    fun `get news returns news item`() = runTest {
        val input = News(
            message = "message",
            url = "url",
            image = "image",
            dateAdded = "2020-01-01"
        )
        val expected = NewsItem(
            message = "message",
            link = "url",
            image = "image",
            date = LocalDate(2020, 1, 1)
        )

        everySuspend { mockFlashbackNewsApi.getOverview() } returns MetadataWrapper(100L, data = listOf(input))

        initUnderTest()
        val result = runBlocking { underTest.getNews() }

        assertEquals(listOf(expected), result)
    }
}
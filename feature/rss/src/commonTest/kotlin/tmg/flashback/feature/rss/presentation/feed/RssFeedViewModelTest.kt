package tmg.flashback.feature.rss.presentation.feed

import app.cash.turbine.test
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.models.ArticleSource
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.feature.rss.usecases.GetRssArticleUseCase
import tmg.flashback.feature.rss.usecases.Response
import tmg.flashback.infrastructure.datetime.TimeManager
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.webbrowser.repository.WebRepository
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RssFeedViewModelTest {

    private val mockRssRepository: RssRepository = mock(autofill)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autofill)
    private val mockGetRssArticlesUseCase: GetRssArticleUseCase = mock(autofill)
    private val mockIsInAppBrowserEnabledUseCase: IsInAppBrowserEnabledUseCase = mock(autofill)
    private val mockWebRepository: WebRepository = mock(autofill)
    private val mockTimeManager: TimeManager = mock(autofill)

    private lateinit var underTest: RSSFeedViewModel

    private fun initUnderTest() {
        every { mockTimeManager.now } returns LocalDateTime.now()
        underTest = RSSFeedViewModel(
            rssRepository = mockRssRepository,
            openWebpageUseCase = mockOpenWebpageUseCase,
            getRssArticlesUseCase = mockGetRssArticlesUseCase,
            isInAppBrowserEnabledUseCase = mockIsInAppBrowserEnabledUseCase,
            webRepository = mockWebRepository,
            timeManager = mockTimeManager,
            coroutineContext = Dispatchers.Unconfined
        )
    }

    @Test
    fun `initial state when no rss urls set`() = runTest {
        every { mockRssRepository.rssUrls } returns emptySet()

        initUnderTest()

        underTest.uiState.test {
            val initial = awaitItem() as RssFeedUiState.Data
            assertEquals(null, initial.lastUpdated)
            assertEquals(false, initial.isLoading)
            assertEquals(emptyList(), initial.rssItems)
            assertEquals(false, initial.hasSources)
        }
    }

    @Test
    fun `rss items are set when initialised`() = runTest {
        val data = listOf(fakeArticle())
        every { mockRssRepository.rssUrls } returns setOf("source")
        everySuspend { mockGetRssArticlesUseCase.invoke() } returns Response.Success(data)

        initUnderTest()

        underTest.uiState.test {
            val successData = awaitItem() as RssFeedUiState.Data
            assertEquals(true, successData.hasSources)
            assertEquals(data, successData.rssItems)
        }
    }

    @Test
    fun `rss items set to no network when rss articles returns error`() = runTest {
        every { mockRssRepository.rssUrls } returns setOf("source")
        everySuspend { mockGetRssArticlesUseCase.invoke() } returns Response.Error()

        initUnderTest()

        underTest.uiState.test {
            assertEquals(RssFeedUiState.NoNetwork, awaitItem())
        }
    }

    @Test
    fun `open article calls open article use case`() {
        every { mockRssRepository.rssUrls } returns emptySet()
        initUnderTest()

        underTest.openArticle(fakeArticle())

        verify {
            mockOpenWebpageUseCase.invoke("link", "title")
        }
    }

    @Test
    fun `is in all browser enabled if web repo is enabled and in app browser is enabled`() {
        every { mockRssRepository.rssUrls } returns emptySet()
        every { mockIsInAppBrowserEnabledUseCase.invoke() } returns true
        every { mockWebRepository.enabled } returns true
        initUnderTest()

        assertTrue(underTest.inAppBrowserEnabled)
    }

    @Test
    fun `is in all browser disabled if web repo is disabled and in app browser is enabled`() {
        every { mockRssRepository.rssUrls } returns emptySet()
        every { mockIsInAppBrowserEnabledUseCase.invoke() } returns true
        every { mockWebRepository.enabled } returns false
        initUnderTest()

        assertFalse(underTest.inAppBrowserEnabled)
    }

    @Test
    fun `is in all browser disabled if web repo is enabled and in app browser is disabled`() {
        every { mockRssRepository.rssUrls } returns emptySet()
        every { mockIsInAppBrowserEnabledUseCase.invoke() } returns false
        every { mockWebRepository.enabled } returns true
        initUnderTest()

        assertFalse(underTest.inAppBrowserEnabled)
    }
}

private fun fakeArticle(
    id: String = "id",
    title: String = "title",
    description: String? = "desc",
    link: String = "link",
    date: LocalDateTime? = LocalDateTime.now(),
    source: ArticleSource = fakeArticleSource()
): Article = Article(
    id = id,
    title = title,
    description = description,
    link = link,
    date = date,
    source = source
)

private fun fakeArticleSource(
    title: String = "title",
    colour: String = "colour",
    textColour: String = "textColour",
    rssLink: String = "rssLink",
    source: String = "source",
    sourceShort: String = "sourceShort",
    contactLink: String = "contactLink"
): ArticleSource = ArticleSource(
    title = title,
    colour = colour,
    textColor = textColour,
    rssLink = rssLink,
    source = source,
    shortSource = sourceShort,
    contactLink = contactLink
)
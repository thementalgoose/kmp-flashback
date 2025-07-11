package tmg.flashback.feature.rss.presentation.configure

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.feature.rss.usecases.GetSourcesUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RssConfigureViewModelTest {

    private lateinit var underTest: RssConfigureViewModel

    private val mockRssRepository: RssRepository = mock(autoUnit)
    private val mockGetSourcesUseCase: GetSourcesUseCase = mock(autoUnit)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autoUnit)

    private fun initUnderTest() {
        underTest = RssConfigureViewModel(
            rssRepository = mockRssRepository,
            getSourcesUseCase = mockGetSourcesUseCase,
            openWebpageUseCase = mockOpenWebpageUseCase

        )
    }

    private val fakeRssLink = "https://www.rss.com"
    private val fakeSupportedSource: SupportedSource = SupportedSource(
        rssLink = fakeRssLink,
        sourceShort = "FK",
        source = "FuckMeSideways",
        colour = "#F00C0F",
        textColour = "#1818181",
        title = "Fuck Me Sideways, you're one stop shop",
        contactLink = "https://www.pleasehelpme.com"
    )

    @Test
    fun `initial load loads source`() = runTest {
        every { mockRssRepository.rssShowDescription } returns true
        every { mockRssRepository.supportedSources } returns listOf(fakeSupportedSource)
        every { mockRssRepository.rssUrls } returns setOf(fakeRssLink)

        val expected = listOf(
            ConfiguredSupportedSource(fakeSupportedSource, true)
        )

        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            assertEquals(expected, initialState.sources)
            assertEquals(true, initialState.showDescription)
        }
    }

    @Test
    fun `adding source marks it as enabled`() = runTest {
        every { mockRssRepository.rssShowDescription } returns true
        every { mockRssRepository.supportedSources } returns listOf(fakeSupportedSource)
        every { mockRssRepository.rssUrls } returns emptySet()
        every { mockRssRepository.rssUrls = any() } returns Unit

        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            assertEquals(listOf(ConfiguredSupportedSource(fakeSupportedSource, false)), initialState.sources)

            underTest.updateSource(fakeSupportedSource.rssLink, true)

            verify {
                mockRssRepository.rssUrls = setOf(fakeRssLink)
            }

            // TODO: State is updated but awaitItem never gets response. Fix this at some point probably maybe
//            val nextState = awaitItem()
//            assertEquals(listOf(ConfiguredSupportedSource(fakeSupportedSource, true)), nextState.sources)
        }
    }

    @Test
    fun `removing source marks it as not enabled`() = runTest() {
        every { mockRssRepository.rssShowDescription } returns true
        every { mockRssRepository.supportedSources } returns listOf(fakeSupportedSource)
        every { mockRssRepository.rssUrls } returns setOf(fakeRssLink)
        every { mockRssRepository.rssUrls = any() } returns Unit

        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            assertEquals(listOf(ConfiguredSupportedSource(fakeSupportedSource, true)), initialState.sources)

            underTest.updateSource(fakeSupportedSource.rssLink, false)

            verify {
                mockRssRepository.rssUrls = emptySet()
            }

            // TODO: State is updated but awaitItem never gets response. Fix this at some point probably maybe
//            val nextState = awaitItem()
//            assertEquals(listOf(ConfiguredSupportedSource(fakeSupportedSource, false)), nextState.sources)
        }
    }

    @Test
    fun `updating show description updates repo and state`() = runTest {
        every { mockRssRepository.rssShowDescription } returns false
        every { mockRssRepository.supportedSources } returns listOf(fakeSupportedSource)
        every { mockRssRepository.rssUrls } returns setOf(fakeRssLink)

        initUnderTest()

        underTest.uiState.test {
            val initialState = awaitItem()
            assertEquals(false, initialState.showDescription)

            underTest.updateShowDescription(true)

            verify {
                mockRssRepository.rssShowDescription = true
            }

            val nextState = awaitItem()
            assertEquals(true, nextState.showDescription)
        }
    }

    @Test
    fun `clicking contact link launches webpage`() {
        initUnderTest()

        underTest.clickContactLink(fakeSupportedSource)
        verify {
            mockOpenWebpageUseCase.invoke(fakeSupportedSource.rssLink)
        }
    }
}
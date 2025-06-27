package tmg.flashback.feature.rss.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.model.SupportedSourceJson
import tmg.flashback.feature.rss.repositories.model.SupportedSourcesJson
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RssRepositoryTest {

    private lateinit var underTest: RssRepositoryImpl

    private val mockConfigManager: ConfigManager = mock(autoUnit)
    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = RssRepositoryImpl(
            configManager = mockConfigManager,
            preferenceManager = mockPreferenceManager
        )
    }

    @Test
    fun `is rss feature enabled returns from config`() {
        every { mockConfigManager.getBoolean(expectedKeyRssEnabled) } returns true
        initUnderTest()
        assertTrue(underTest.isEnabled)
    }

    @Test
    fun `is add custom sources enabled returns from config`() {
        every { mockConfigManager.getBoolean(expectedKeyAddCustomSourcesEnabled) } returns true
        initUnderTest()
        assertTrue(underTest.isAddCustomSourcesEnabled)
    }

    @Test
    fun `getting supported sources returns empty list if manager sources null`() {
        every { mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer()) } returns null
        initUnderTest()
        assertEquals(emptyList<SupportedSource>(), underTest.supportedSources)
        verify {
            mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer())
        }
    }

    @Test
    fun `getting supported sources returns empty list if model sources is null`() {
        every { mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer()) } returns SupportedSourcesJson(sources = null)
        initUnderTest()
        assertEquals(emptyList<SupportedSource>(), underTest.supportedSources)
        verify {
            mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer())
        }
    }

    @Test
    fun `getting supported sources returns empty list if model sources is empty`() {
        every { mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer()) } returns SupportedSourcesJson(sources = emptyList())
        initUnderTest()
        assertEquals(emptyList<SupportedSource>(), underTest.supportedSources)
        verify {
            mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer())
        }
    }

    @Test
    fun `getting supported sources returns valid list when json content`() {
        every { mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer()) } returns SupportedSourcesJson(sources = listOf(SupportedSourceJson(
            rssLink = "rssLink",
            source = "source",
            sourceShort = "sourceShort",
            colour = "color",
            textColour = "color",
            title = "title",
            contactLink = "contact"
        )))
        initUnderTest()
        assertEquals("rssLink", underTest.supportedSources.first().rssLink)
        verify {
            mockConfigManager.getJson(expectedKeyRssSupportedSources, SupportedSourcesJson.serializer())
        }
    }

    //endregion

    //region rssUrls

    @Test
    fun `get rss urls reads value from preferences repository`() {
        every { mockPreferenceManager.getSet(expectedKeyRssList, emptySet()) } returns mutableSetOf("test")
        initUnderTest()

        assertEquals(mutableSetOf("test"), underTest.rssUrls)
        verify {
            mockPreferenceManager.getSet(expectedKeyRssList, emptySet())
        }
    }

    @Test
    fun `get rss urls saves value to shared prefs repository`() {
        initUnderTest()

        underTest.rssUrls = setOf("hey")
        verify {
            mockPreferenceManager.save(expectedKeyRssList, setOf("hey"))
        }
    }

    //endregion

    //region rssShowDescription

    @Test
    fun `rss show description reads value from preferences repository`() {
        every { mockPreferenceManager.getBoolean(expectedKeyRssShowDescription, true) } returns true
        initUnderTest()

        assertTrue(underTest.rssShowDescription)
        verify {
            mockPreferenceManager.getBoolean(expectedKeyRssShowDescription, true)
        }
    }

    @Test
    fun `rss show description saves value to shared prefs repository`() {
        initUnderTest()

        underTest.rssShowDescription = true
        verify {
            mockPreferenceManager.save(expectedKeyRssShowDescription, true)
        }
    }

    //endregion

    companion object {

        private const val expectedKeyRssEnabled = "rss"
        private const val expectedKeyAddCustomSourcesEnabled = "rss_add_custom"
        private const val expectedKeyRssSupportedSources: String = "rss_supported_sources"

        // Prefs
        private const val expectedKeyRssList: String = "RSS_LIST"
        private const val expectedKeyRssShowDescription: String = "NEWS_SHOW_DESCRIPTIONS"
    }
}
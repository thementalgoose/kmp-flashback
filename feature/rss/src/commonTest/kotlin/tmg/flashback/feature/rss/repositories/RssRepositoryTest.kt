package tmg.flashback.feature.rss.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test
import kotlin.test.assertTrue

internal class RssRepositoryTest {

    private lateinit var underTest: RssRepositoryImpl

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = RssRepositoryImpl(
            configManager = mockConfigManager
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

    companion object {

        private const val expectedKeyRssEnabled = "rss"
        private const val expectedKeyAddCustomSourcesEnabled = "rss_add_custom"
    }
}
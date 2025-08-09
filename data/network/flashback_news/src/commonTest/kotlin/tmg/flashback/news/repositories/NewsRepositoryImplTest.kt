package tmg.flashback.news.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NewsRepositoryImplTest {

    private lateinit var underTest: NewsRepositoryImpl

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = NewsRepositoryImpl(
            configManager = mockConfigManager
        )
    }

    @Test
    fun `base url falls back to known fallback if not available in config`() {

        every { mockConfigManager.getString(expectedKeyBaseUrl) } returns null

        initUnderTest()
        assertEquals(expectedFallbackUrl, underTest.baseUrl)
        verify {
            mockConfigManager.getString(expectedKeyBaseUrl)
        }
    }

    @Test
    fun `base url falls returns config value if exists`() {
        initUnderTest()
        every { mockConfigManager.getString(expectedKeyBaseUrl) } returns "https://flashback.custom.dev"

        assertEquals("https://flashback.custom.dev", underTest.baseUrl)
    }

    companion object {
        private const val expectedFallbackUrl: String = "https://flashback.pages.dev"
        private const val expectedKeyBaseUrl: String = "config_url"
    }
}
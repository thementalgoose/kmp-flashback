package tmg.flashback.flashbackapi.api.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NetworkConfigRepositoryTest {

    private lateinit var underTest: NetworkConfigRepositoryImpl

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = NetworkConfigRepositoryImpl(
            configManager = mockConfigManager
        )
    }

    @Test
    fun `base url falls back to known fallback if not available in config`() {
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

        assertEquals(expectedFallbackUrl, underTest.baseUrl)
    }

    companion object {
        private const val expectedFallbackUrl: String = "https://flashback.pages.dev"
        private const val expectedKeyBaseUrl: String = "config_url"
    }
}
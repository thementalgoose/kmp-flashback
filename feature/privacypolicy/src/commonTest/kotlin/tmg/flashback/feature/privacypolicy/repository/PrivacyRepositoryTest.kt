package tmg.flashback.feature.privacypolicy.repository

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PrivacyRepositoryTest {

    private lateinit var underTest: PrivacyRepositoryImpl

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = PrivacyRepositoryImpl(
            configManager = mockConfigManager
        )
    }

    @Test
    fun `getting privacy policy url returns from config`() {
        val policy = "test_policy"
        every { mockConfigManager.getString(expectedKeyPrivacyPolicyUrl) } returns policy
        initUnderTest()

        assertEquals(policy, underTest.privacyPolicyUrl)
    }

    companion object {
        private const val expectedKeyPrivacyPolicyUrl = "privacy_policy_url"
    }
}
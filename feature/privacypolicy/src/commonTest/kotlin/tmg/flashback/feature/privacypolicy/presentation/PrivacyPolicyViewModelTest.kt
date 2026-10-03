package tmg.flashback.feature.privacypolicy.presentation

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode.Companion.exactly
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.feature.privacypolicy.repository.PrivacyRepository
import org.junit.jupiter.api.Test

internal class PrivacyPolicyViewModelTest {

    private lateinit var underTest: PrivacyPolicyViewModel

    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autoUnit)
    private val mockPrivacyRepository: PrivacyRepository = mock(autofill)

    private fun initUnderTest(policyUrl: String? = null) {
        every { mockPrivacyRepository.privacyPolicyUrl } returns policyUrl

        underTest = PrivacyPolicyViewModel(
            openWebpageUseCase = mockOpenWebpageUseCase,
            privacyRepository = mockPrivacyRepository
        )
    }

    @Test
    fun `openPolicy when privacyPolicyUrl is null does not open webpage`() {
        initUnderTest(policyUrl = null)

        underTest.openPolicy()

        verify(exactly(0)) {
            mockOpenWebpageUseCase.invoke(any())
        }
    }

    @Test
    fun `openPolicy when privacyPolicyUrl is set opens webpage`() {
        initUnderTest(policyUrl = "https://example.com/privacy")

        underTest.openPolicy()

        verify {
            mockOpenWebpageUseCase.invoke("https://example.com/privacy")
        }
    }

    @Test
    fun `openWebpage opens specified url`() {
        initUnderTest()

        underTest.openWebpage("https://example.com/terms")

        verify {
            mockOpenWebpageUseCase.invoke("https://example.com/terms")
        }
    }
}

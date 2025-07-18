package tmg.flashback.usecases

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.configuration.usecases.DoesConfigRequireSyncUseCase
import tmg.flashback.repositories.OnboardingRepository
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RequiresSyncUseCaseTest {

    private lateinit var underTest: RequiresSyncUseCaseImpl

    private val mockDoesRequireSyncUseCase: DoesConfigRequireSyncUseCase = mock(autoUnit)
    private val mockOnboardingRepository: OnboardingRepository = mock(autoUnit)

    private fun initUnderTest() {
        underTest = RequiresSyncUseCaseImpl(
            doesConfigRequireSyncUseCase = mockDoesRequireSyncUseCase,
            onboardingRepository = mockOnboardingRepository
        )
    }

    @Test
    fun `app requires sync if config not synced and content not synced`() {
        every { mockDoesRequireSyncUseCase.invoke() } returns true
        every { mockOnboardingRepository.initialSyncCompleted } returns false

        initUnderTest()

        assertTrue(underTest())
    }

    @Test
    fun `app requires sync if config synced and content not synced`() {
        every { mockDoesRequireSyncUseCase.invoke() } returns false
        every { mockOnboardingRepository.initialSyncCompleted } returns false

        initUnderTest()

        assertTrue(underTest())
    }

    @Test
    fun `app requires sync if config not synced and content synced`() {
        every { mockDoesRequireSyncUseCase.invoke() } returns true
        every { mockOnboardingRepository.initialSyncCompleted } returns true

        initUnderTest()

        assertTrue(underTest())
    }

    @Test
    fun `app doesnt require sync if config doesnt require sync and content synced completed`() {
        every { mockDoesRequireSyncUseCase.invoke() } returns false
        every { mockOnboardingRepository.initialSyncCompleted } returns true

        initUnderTest()

        assertFalse(underTest())
    }
}
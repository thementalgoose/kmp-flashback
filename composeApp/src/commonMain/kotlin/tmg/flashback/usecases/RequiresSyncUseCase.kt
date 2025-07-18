package tmg.flashback.usecases

import tmg.flashback.configuration.usecases.DoesConfigRequireSyncUseCase
import tmg.flashback.repositories.OnboardingRepository

interface RequiresSyncUseCase {
    operator fun invoke(): Boolean
}

internal class RequiresSyncUseCaseImpl(
    private val doesConfigRequireSyncUseCase: DoesConfigRequireSyncUseCase,
    private val onboardingRepository: OnboardingRepository
): RequiresSyncUseCase {
    override fun invoke() = doesConfigRequireSyncUseCase() || !onboardingRepository.initialSyncCompleted
}
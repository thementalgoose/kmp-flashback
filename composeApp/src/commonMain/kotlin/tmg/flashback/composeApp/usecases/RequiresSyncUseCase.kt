package tmg.flashback.composeApp.usecases

import org.koin.core.annotation.Single
import tmg.flashback.configuration.usecases.DoesConfigRequireSyncUseCase
import tmg.flashback.composeApp.repositories.OnboardingRepository

interface RequiresSyncUseCase {
    operator fun invoke(): Boolean
}

@Single(binds = [RequiresSyncUseCase::class])
internal class RequiresSyncUseCaseImpl(
    private val doesConfigRequireSyncUseCase: DoesConfigRequireSyncUseCase,
    private val onboardingRepository: OnboardingRepository
): RequiresSyncUseCase {
    override fun invoke() = doesConfigRequireSyncUseCase() || !onboardingRepository.initialSyncCompleted
}
package tmg.flashback.usecases

import tmg.flashback.configuration.usecases.DoesConfigRequireSyncUseCase
import tmg.flashback.repositories.ContentSyncRepository

interface RequiresSyncUseCase {
    operator fun invoke(): Boolean
}

internal class RequiresSyncUseCaseImpl(
    private val doesConfigRequireSyncUseCase: DoesConfigRequireSyncUseCase,
    private val contentSyncRepository: ContentSyncRepository
): RequiresSyncUseCase {
    override fun invoke() = doesConfigRequireSyncUseCase() || !contentSyncRepository.initialSyncCompleted
}
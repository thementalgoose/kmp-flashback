package tmg.flashback.configuration.usecases

import tmg.flashback.configuration.repositories.ConfigRepository

interface DoesConfigRequireSyncUseCase {
    operator fun invoke(): Boolean
}

internal class DoesConfigRequireSyncUseCaseImpl(
    private val configRepository: ConfigRepository
): DoesConfigRequireSyncUseCase {
    override fun invoke(): Boolean {
        return configRepository.requireSynchronisation
    }
}
package tmg.flashback.configuration.usecases

import org.koin.core.annotation.Single
import tmg.flashback.configuration.repositories.ConfigRepository

interface DoesConfigRequireSyncUseCase {
    operator fun invoke(): Boolean
}

@Single
internal class DoesConfigRequireSyncUseCaseImpl(
    private val configRepository: ConfigRepository
): DoesConfigRequireSyncUseCase {
    override fun invoke(): Boolean {
        return configRepository.requireSynchronisation
    }
}
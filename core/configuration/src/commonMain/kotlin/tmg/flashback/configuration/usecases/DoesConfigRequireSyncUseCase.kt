package tmg.flashback.configuration.usecases

import org.koin.core.annotation.Single
import tmg.flashback.configuration.repositories.ConfigRepository

interface DoesConfigRequireSyncUseCase {
    operator fun invoke(): Boolean
}

@Single(binds = [DoesConfigRequireSyncUseCase::class])
class DoesConfigRequireSyncUseCaseImpl(
    private val configRepository: ConfigRepository
): DoesConfigRequireSyncUseCase {
    override fun invoke(): Boolean {
        return configRepository.requireSynchronisation
    }
}
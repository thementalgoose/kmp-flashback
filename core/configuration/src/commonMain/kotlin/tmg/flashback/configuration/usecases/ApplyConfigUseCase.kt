package tmg.flashback.configuration.usecases

import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.repositories.ConfigRepository

interface ApplyConfigUseCase {
    suspend operator fun invoke(): Boolean
}

internal class ApplyConfigUseCaseImpl(
    private val configManager: ConfigManager,
    private val configRepository: ConfigRepository
): ApplyConfigUseCase {
    override suspend operator fun invoke(): Boolean {
        val result = configManager.activate()
        if (result) {
            configRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
        return result
    }
}
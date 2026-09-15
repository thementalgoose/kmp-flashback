package tmg.flashback.configuration.usecases

import org.koin.core.annotation.Single
import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.repositories.ConfigRepository

interface ResetConfigUseCase {
    suspend fun reset(): Boolean
    suspend fun ensureReset(): Boolean
}

@Single(binds = [ResetConfigUseCase::class])
class ResetConfigUseCaseImpl(
    private val configManager: ConfigManager,
    private val configRepository: ConfigRepository
): ResetConfigUseCase {
    override suspend fun reset(): Boolean {
        configRepository.remoteConfigSync = 0
        return configManager.reset()
    }

    override suspend fun ensureReset(): Boolean {
        val existing = configRepository.resetAtMigrationVersion
        if (existing != Migrations.configurationSyncCount) {
            configRepository.resetAtMigrationVersion = Migrations.configurationSyncCount
            configManager.reset()
        }
        return true
    }
}
package tmg.flashback.configuration.usecases

import org.koin.core.annotation.Single
import tmg.flashback.configuration.Migrations
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.configuration.repositories.ConfigRepository

interface FetchConfigUseCase {
    suspend fun fetch(): Boolean
    suspend fun fetchAndApply(): Boolean
}

@Single(binds = [FetchConfigUseCase::class])
class FetchConfigUseCaseImpl(
    private val configManager: ConfigManager,
    private val configRepository: ConfigRepository
): FetchConfigUseCase {
    override suspend fun fetch() = fetch(false)

    override suspend fun fetchAndApply() = fetch(true)

    private suspend fun fetch(apply: Boolean): Boolean {
        val result = configManager.fetch(apply)
        if (result && apply) {
            configRepository.remoteConfigSync = Migrations.configurationSyncCount
        }
        return result
    }
}
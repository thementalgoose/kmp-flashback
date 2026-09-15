package tmg.flashback.flashbackapi.api.repositories

import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import tmg.flashback.configuration.manager.ConfigManager

private const val FALLBACK_BASE_URL = "https://flashback.pages.dev"

interface NetworkConfigRepository {
    val baseUrl: String
}

@Single(binds = [NetworkConfigRepository::class])
internal class NetworkConfigRepositoryImpl(
    @Provided private val configManager: ConfigManager
): NetworkConfigRepository {

    override val baseUrl: String
        get() = configManager.getString(keyBaseUrl)
            ?.ifEmpty { FALLBACK_BASE_URL }
            ?: FALLBACK_BASE_URL

    companion object {
        private const val keyBaseUrl: String = "config_url"
    }
}
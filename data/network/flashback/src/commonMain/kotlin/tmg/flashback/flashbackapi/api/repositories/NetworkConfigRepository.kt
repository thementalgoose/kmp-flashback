package tmg.flashback.flashbackapi.api.repositories

import tmg.flashback.configuration.manager.ConfigManager

private const val FALLBACK_BASE_URL = "https://flashback.pages.dev"

interface NetworkConfigRepository {
    val baseUrl: String
}

internal class NetworkConfigRepositoryImpl(
    private val configManager: ConfigManager
): NetworkConfigRepository {

    override val baseUrl: String
        get() = configManager.getString(keyBaseUrl) ?: FALLBACK_BASE_URL

    companion object {
        private const val keyBaseUrl: String = "config_url"
    }
}
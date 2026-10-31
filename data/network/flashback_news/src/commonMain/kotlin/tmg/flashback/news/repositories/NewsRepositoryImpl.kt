package tmg.flashback.news.repositories

import org.koin.core.annotation.Single
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.text.ifEmpty

private const val FALLBACK_BASE_URL = "https://flashback.pages.dev"

@Single
internal class NewsRepositoryImpl(
    private val configManager: ConfigManager
): NewsRepository {

    override val baseUrl: String
        get() = configManager.getString(keyBaseUrl)
            ?.ifEmpty { FALLBACK_BASE_URL }
            ?: FALLBACK_BASE_URL

    companion object {
        private const val keyBaseUrl: String = "config_url"
    }
}
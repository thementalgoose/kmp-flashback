package tmg.flashback.feature.rss.repositories

import tmg.flashback.configuration.manager.ConfigManager

interface RssRepository {
    val isEnabled: Boolean
    val isAddCustomSourcesEnabled: Boolean
}

internal class RssRepositoryImpl(
    private val configManager: ConfigManager
): RssRepository {

    override val isEnabled: Boolean
        get() = configManager.getBoolean(keyRssEnabled)

    override val isAddCustomSourcesEnabled: Boolean
        get() = configManager.getBoolean(keyAddCustomSourcesEnabled)

    companion object {
        private const val keyRssEnabled = "rss"
        private const val keyAddCustomSourcesEnabled = "rss_add_custom"
    }
}
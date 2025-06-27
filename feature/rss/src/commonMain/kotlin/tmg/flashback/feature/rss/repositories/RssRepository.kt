package tmg.flashback.feature.rss.repositories

import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.mapper.convert
import tmg.flashback.feature.rss.repositories.model.SupportedSourcesJson
import tmg.flashback.preferences.manager.PreferenceManager

interface RssRepository {
    val isEnabled: Boolean
    val isAddCustomSourcesEnabled: Boolean
    val supportedSources: List<SupportedSource>
    var rssUrls: Set<String>
    var rssShowDescription: Boolean
}

internal class RssRepositoryImpl(
    private val configManager: ConfigManager,
    private val preferenceManager: PreferenceManager
): RssRepository {

    override val isEnabled: Boolean
        get() = configManager.getBoolean(keyRssEnabled)

    override val isAddCustomSourcesEnabled: Boolean
        get() = configManager.getBoolean(keyAddCustomSourcesEnabled)

    override val supportedSources: List<SupportedSource> by lazy {
        configManager
            .getJson(keyRssSupportedSources, SupportedSourcesJson.serializer())
            ?.convert()
            ?: emptyList()
    }

    override var rssUrls: Set<String>
        get() = preferenceManager.getSet(keyRssList, emptySet())
        set(value) = preferenceManager.save(keyRssList, value)

    override var rssShowDescription: Boolean
        get() = preferenceManager.getBoolean(keyRssShowDescription, true)
        set(value) = preferenceManager.save(keyRssShowDescription, value)

    companion object {
        private const val keyRssEnabled = "rss"
        private const val keyAddCustomSourcesEnabled = "rss_add_custom"
        private const val keyRssSupportedSources: String = "rss_supported_sources"

        // Prefs
        private const val keyRssList: String = "RSS_LIST"
        private const val keyRssShowDescription: String = "NEWS_SHOW_DESCRIPTIONS"
    }
}
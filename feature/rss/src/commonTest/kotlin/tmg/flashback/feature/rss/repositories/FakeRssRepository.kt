package tmg.flashback.feature.rss.repositories

import tmg.flashback.feature.rss.models.SupportedSource

class FakeRssRepository(
    override var isEnabled: Boolean = false,
    override var isAddCustomSourcesEnabled: Boolean = false,
    override var supportedSources: List<SupportedSource> = listOf(),
    override var rssUrls: Set<String> = setOf(),
    override var rssShowDescription: Boolean = false
) : RssRepository
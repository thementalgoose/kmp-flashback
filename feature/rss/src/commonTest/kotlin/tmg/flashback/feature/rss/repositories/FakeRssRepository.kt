package tmg.flashback.feature.rss.repositories

import tmg.flashback.feature.rss.models.SupportedSource

class FakeRssRepository(
    override var isEnabled: Boolean = false,
    override var isAddCustomSourcesEnabled: Boolean = false,
    override var supportedSources: List<SupportedSource>,
    override var rssUrls: Set<String>,
    override var rssShowDescription: Boolean
) : RssRepository
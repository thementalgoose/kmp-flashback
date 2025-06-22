package tmg.flashback.feature.rss.repositories

class FakeRssRepository(
    override var isEnabled: Boolean = false,
    override var isAddCustomSourcesEnabled: Boolean = false
) : RssRepository
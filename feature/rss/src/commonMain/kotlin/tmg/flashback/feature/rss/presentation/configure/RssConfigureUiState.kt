package tmg.flashback.feature.rss.presentation.configure

import tmg.flashback.feature.rss.models.ArticleSource
import tmg.flashback.feature.rss.models.SupportedSource

data class RssConfigureUiState(
    val sources: List<ConfiguredSupportedSource>,
    val showDescription: Boolean,
    val showAddCustom: Boolean,
)

data class ConfiguredSupportedSource(
    val article: SupportedSource,
    val isEnabled: Boolean
)
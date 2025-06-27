package tmg.flashback.feature.rss.presentation.feed

import tmg.flashback.feature.rss.models.Article

sealed class RssFeedUiState {
    data class Data(
        val isLoading: Boolean,
        val lastUpdated: String? = null,
        val rssItems: List<Article> = emptyList(),
        val hasSources: Boolean = false,
    ): RssFeedUiState()

    data object NoNetwork: RssFeedUiState()
}
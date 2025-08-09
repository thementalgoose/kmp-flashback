package tmg.flashback.feature.highlights.presentation

import tmg.flashback.feature.highlights.domain.models.NewsItem

data class HighlightsUiState(
    val news: List<NewsItem>?,
    val show: Boolean
)
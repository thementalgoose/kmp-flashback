package tmg.flashback.highlights.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class HighlightsUiState(
    val news: List<HighlightNewsItem>?,
    val show: Boolean
)

data class HighlightNewsItem(
    val message: String,
    val link: String?,
    val image: String?,
    val date: LocalDate
)
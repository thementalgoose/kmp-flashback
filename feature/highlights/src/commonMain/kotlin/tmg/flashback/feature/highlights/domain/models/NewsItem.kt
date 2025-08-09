package tmg.flashback.feature.highlights.domain.models

import kotlinx.datetime.LocalDate

data class NewsItem(
    val message: String,
    val link: String?,
    val image: String?,
    val date: LocalDate
)
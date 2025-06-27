package tmg.flashback.feature.rss.models

import kotlinx.datetime.LocalDateTime

data class Article(
    val id: String,
    val title: String,
    val description: String?,
    val link: String,
    val date: LocalDateTime?,
    val source: ArticleSource
)
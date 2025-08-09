package tmg.flashback.feature.highlights.domain

import tmg.flashback.feature.highlights.domain.models.NewsItem

interface GetNewsItemsUseCase {
    suspend fun getNews(): List<NewsItem>?
}
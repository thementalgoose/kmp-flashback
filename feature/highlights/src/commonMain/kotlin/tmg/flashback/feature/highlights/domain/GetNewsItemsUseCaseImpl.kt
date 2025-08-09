package tmg.flashback.feature.highlights.domain

import tmg.flashback.feature.highlights.domain.models.NewsItem
import tmg.flashback.infrastructure.datetime.requireFromDate
import tmg.flashback.news.api.FlashbackNewsApi
import tmg.flashback.news.models.news.News

class GetNewsItemsUseCaseImpl(
    private val flashbackNewsApi: FlashbackNewsApi
): GetNewsItemsUseCase {

    override suspend fun getNews(): List<NewsItem>? {
        return flashbackNewsApi.getOverview()
            ?.data
            ?.mapNotNull { it.toDomain() }
    }

    private fun News.toDomain(): NewsItem? = try {
        NewsItem(
            message = this.message,
            link = this.url,
            image = this.image,
            date = requireFromDate(this.dateAdded)
        )
    } catch (e: RuntimeException) {
        null
    }
}
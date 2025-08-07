package tmg.flashback.feature.rss.usecases

import tmg.flashback.feature.rss.models.Article

interface GetRssArticlesUseCase {
    suspend operator fun invoke(): Response
}

sealed interface Response {
    data class Success(
        val data: List<Article>
    ): Response
    data class Error(
        val code: Int? = null
    ): Response
}
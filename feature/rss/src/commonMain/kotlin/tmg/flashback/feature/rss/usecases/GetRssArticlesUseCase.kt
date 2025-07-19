package tmg.flashback.feature.rss.usecases

import coil3.Uri
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import tmg.flashback.feature.rss.mapper.RssXMLMapper
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.api.RssApi

interface GetRssArticleUseCase {
    suspend operator fun invoke(): Response
}

class GetRssArticlesUseCaseImpl(
    private val rssRepository: RssRepository,
    private val rssApi: RssApi,
    private val rssXmlMapper: RssXMLMapper
): GetRssArticleUseCase {
    override suspend operator fun invoke(): Response {
        val responses = getAll()
        val errors = responses.filter { it is Response.Error }
        if (responses.size != errors.size || responses.isEmpty()) {
            // Only some requests failed, continue with list
            val validResponses = responses
                .filterIsInstance<Response.Success>()
                .map { it.data }
                .flatten()
                .sortedByDescending { it.date }

            return Response.Success(validResponses)
        } else {
            // All failed
            return Response.Error(null)
        }
    }


    private suspend fun getAll(): List<Response> = coroutineScope {
        if (rssRepository.rssUrls.isEmpty()) {
            return@coroutineScope emptyList()
        }
        return@coroutineScope rssRepository.rssUrls
            .map { async { get(it) } }
            .awaitAll()

    }

    private suspend fun get(url: String): Response {
        return try {
            val normalisedUrl = url.replace("http://", "https://").trim()
            if (normalisedUrl.isBlank() || !normalisedUrl.startsWith("https://")) {
                return Response.Error(400)
            }
            val response = rssApi.getRssXML(normalisedUrl)
            val model = rssXmlMapper.convert(response, normalisedUrl, showDescription = rssRepository.rssShowDescription)
            Response.Success(model)
        } catch (e: Throwable) {
            logDebug("RSS", "EXCEPTION ${e.message}")
            e.printStackTrace()
            Response.Error(500)
        }
    }
}

sealed interface Response {
    data class Success(
        val data: List<Article>
    ): Response
    data class Error(
        val code: Int? = null
    ): Response
}
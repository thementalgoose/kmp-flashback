package tmg.flashback.feature.rss.usecases

import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import tmg.flashback.feature.rss.mapper.RssXMLMapper
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.network.rss.api.RssApi

@Single(binds = [GetRssArticlesUseCase::class])
class GetRssArticlesUseCaseImpl(
    private val rssRepository: RssRepository,
    @Provided private val rssApi: RssApi,
    @Provided private val rssXmlMapper: RssXMLMapper
): GetRssArticlesUseCase {
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
        if (rssRepository.rssUrls.none { it.isNotBlank() }) {
            return@coroutineScope emptyList()
        }
        return@coroutineScope rssRepository.rssUrls
            .filter { it.isNotBlank() }
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
package tmg.flashback.news.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.news.client.json
import tmg.flashback.news.models.MetadataWrapper
import tmg.flashback.news.models.news.News
import tmg.flashback.news.repositories.NewsRepository

internal class FlashbackNewsApiImpl(
    private val httpClient: HttpClient,
    private val newsRepository: NewsRepository
): FlashbackNewsApi {

    override suspend fun getOverview(): MetadataWrapper<List<News>>? =
        makeRequest("news.json")

    private val baseUrl: String
        get() = newsRepository.baseUrl

    @Throws(RuntimeException::class, IOException::class)
    private suspend inline fun <reified T> makeRequest(endpoint: String): T? {
        logInfo(">> $baseUrl/$endpoint")
        val httpResponse = httpClient.get("$baseUrl/$endpoint") {
            headers {
                set("Accept", "application/json")
                set("ContentType", "application/json")
                set("content-type", "application/json")
            }
        }
        when (httpResponse.status) {
            HttpStatusCode.NotFound -> {
                throw RuntimeException()
            }
            HttpStatusCode.InternalServerError -> {
                throw RuntimeException()
            }
        }
        // body decoding ktor strategy not working for some reason
        return json.decodeFromString<T>(httpResponse.bodyAsText())
    }
}

package tmg.flashback.network.rss.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import kotlinx.io.IOException
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.models.RssXMLModel

class RssApiImpl(
    private val httpClient: HttpClient
): RssApi {

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getRssXML(url: String): RssXMLModel {
        logInfo(">> $url")
        val httpResponse = httpClient.get(url) {
            headers {
                this.set("Accept", "application/rss+xml, application/xml")
            }
        }
        when (httpResponse.status) {
            HttpStatusCode.NotFound -> {
                throw NotFoundException()
            }
            HttpStatusCode.InternalServerError -> {
                throw RuntimeException()
            }
        }
        return httpResponse.body()
    }
}

class NotFoundException(): RuntimeException()
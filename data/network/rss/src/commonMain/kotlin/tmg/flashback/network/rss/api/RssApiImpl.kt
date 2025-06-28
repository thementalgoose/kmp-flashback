package tmg.flashback.network.rss.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.client.xml
import tmg.flashback.network.rss.models.RssXMLModel

class RssApiImpl(
    private val httpClient: HttpClient
): RssApi {

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getRssXML(url: String): RssXMLModel {
        logInfo(">> $url")
        val httpResponse = httpClient.get(url)
        when (httpResponse.status) {
            HttpStatusCode.NotFound -> {
                throw NotFoundException()
            }
            HttpStatusCode.InternalServerError -> {
                throw RuntimeException()
            }
        }
        return xml.decodeFromString<RssXMLModel>(RssXMLModel.serializer(), httpResponse.bodyAsText())
    }
}

class NotFoundException(): RuntimeException()
package tmg.flashback.network.rss.api

import kotlinx.io.IOException
import tmg.flashback.network.rss.models.RssXMLModel

interface RssApi {

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getRssXML(url: String): RssXMLModel
}
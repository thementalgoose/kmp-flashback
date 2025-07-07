package tmg.flashback.network.rss.api

import kotlinx.io.IOException
import tmg.flashback.network.rss.models.RssFeed

interface RssApi {

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getRssXML(url: String): RssFeed
}
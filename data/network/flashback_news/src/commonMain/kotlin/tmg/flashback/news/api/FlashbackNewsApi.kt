package tmg.flashback.news.api

import kotlinx.io.IOException
import tmg.flashback.news.models.MetadataWrapper
import tmg.flashback.news.models.news.News

interface FlashbackNewsApi {

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getOverview(): MetadataWrapper<List<News>>?
}
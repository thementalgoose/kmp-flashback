package tmg.flashback.feature.rss.mapper

import io.ktor.http.URLProtocol.Companion.HTTP
import io.ktor.http.URLProtocol.Companion.HTTPS
import io.ktor.http.Url
import kotlinx.datetime.LocalDateTime
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.models.ArticleSource
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.network.rss.models.RssXMLModel
import tmg.flashback.network.rss.models.RssXMLModelItem

interface RssXMLMapper {
    fun convert(
        model: RssXMLModel,
        fromSource: String,
        showDescription: Boolean
    ): List<Article>
}

internal class RssXMLMapperImpl(): RssXMLMapper {
    override fun convert(
        model: RssXMLModel,
        fromSource: String,
        showDescription: Boolean
    ): List<Article> {

        if (model.channel == null) {
            logDebug("RSS", "Failed to parse RSS model from channel $fromSource")
            return emptyList()
        }

        if (model.channel?.title == null) {
            logDebug("RSS", "Failed to parse RSS model from title $fromSource")
            return emptyList()
        }

        var url = model.channel?.link ?: ""
        if (model.channel?.link == null) {
            url = extractLinkSource(model.channel?.item) ?: ""
            if (url.isEmpty()) {
                logDebug("RSS", "Failed to parse RSS model from link $fromSource")
                return emptyList()
            }
        }

        val source = ArticleSource(
            title = model.channel!!.title!!,
            colour = "#4A34B6",
            textColor = "#FFFFFF",
            source = url,
            shortSource = null,
            rssLink = fromSource,
            contactLink = null
        )

        return model.channel
            ?.item
            ?.filter { it?.title != null && it.link != null && it.pubDate != null }
            ?.filterNotNull()
            ?.map {
                Article(
                    id = it.link!!,
                    title = it.title!!.replace("&#039;", "'"),
                    description = when (showDescription) {
                        false -> null
                        true -> it.description
                            ?.substringBefore("&lt;/p&gt;")
                            ?.substringBefore("</p>")
                            ?.trim()
                    },
                    link = it.link!!.replace("http://", "https://"),
                    date = LocalDateTime.parse(it.pubDate!!.replace("GMT", "+0000")),
                    source = source
                )
            } ?: emptyList()
    }
}

private fun extractLinkSource(items: List<RssXMLModelItem?>?): String? {
    val urlString = (items ?: emptyList()).firstNotNullOfOrNull { it?.link } ?: return null
    val url = Url(urlString)
    if (url.protocol != HTTP || url.protocol != HTTPS) {
        return null
    }
    return "${url.protocol}://${url.host}"
}
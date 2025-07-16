package tmg.flashback.feature.rss.mapper

import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlHandler
import com.mohamedrejeb.ksoup.html.parser.KsoupHtmlParser
import io.ktor.http.URLProtocol.Companion.HTTP
import io.ktor.http.URLProtocol.Companion.HTTPS
import io.ktor.http.Url
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames.Companion.ENGLISH_ABBREVIATED
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.format.optional
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.models.ArticleSource
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.infrastructure.log.logInfo
import tmg.flashback.network.rss.models.Item
import tmg.flashback.network.rss.models.RssFeed

const val RSS_DESC_LENGTH = 500

interface RssXMLMapper {
    fun convert(
        model: RssFeed,
        fromSource: String,
        showDescription: Boolean
    ): List<Article>
}

internal class RssXMLMapperImpl(
    private val supportedSourceMapper: SupportedSourceMapper
): RssXMLMapper {
    override fun convert(
        model: RssFeed,
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

        val source = supportedSourceMapper.invoke(model.channel?.link) ?: ArticleSource(
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
            ?.filter { it.title != null && it.link != null }
            ?.filterNotNull()
            ?.map {
                val pubDate = it.pubDate ?: LocalDateTime.now().format(LocalDateTime.Formats.ISO)
                val desc = it.description
                    ?.substringBefore("&lt;/p&gt;")
                    ?.substringBefore("</p>")
                    ?.trim()
                val description = try {
                    when (showDescription) {
                        false -> null
                        true -> {
                            var string = ""
                            val rawTextHandler = KsoupHtmlHandler
                                .Builder()
                                .onText { text -> string += text }
                                .build()
                            val parser = KsoupHtmlParser(rawTextHandler)
                            parser.write(desc ?: "")
                            string.take(RSS_DESC_LENGTH)
                        }
                    }
                } catch (_: Exception) {
                    desc?.take(RSS_DESC_LENGTH)
                }

                Article(
                    id = it.link!!,
                    title = it.title!!.replace("&#039;", "'"),
                    description = description,
                    link = it.link!!.replace("http://", "https://"),
                    date = attemptParseDate(pubDate
                        .split("+")[0]
                        .replace("GMT", "")
                        .trim()),
                    source = source
                )
            } ?: emptyList()
    }
}

private fun attemptParseDate(date: String): LocalDateTime {
    logInfo("Attempting to parse '$date'")
    for (x in rssDateTimeFormats) {
        try {
            return LocalDateTime.parse(date, x)
        } catch (e: Exception) {

        }
    }
    return LocalDateTime.now()
}

val rssDateTimeFormats = listOf(
    LocalDateTime.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
        chars(", ")
        dayOfMonth(Padding.ZERO)
        char(' ')
        monthName(ENGLISH_ABBREVIATED)
        char(' ')
        year()
        char(' ')
        hour(Padding.ZERO)
        char(':')
        minute(Padding.ZERO)
        char(':')
        second(Padding.ZERO)
        optional {
            char(' ')
        }
    },
    LocalDateTime.Formats.ISO,
)

private fun extractLinkSource(items: List<Item?>?): String? {
    val urlString = (items ?: emptyList()).firstNotNullOfOrNull { it?.link } ?: return null
    val url = Url(urlString)
    if (url.protocol != HTTP || url.protocol != HTTPS) {
        return null
    }
    return "${url.protocol}://${url.host}"
}
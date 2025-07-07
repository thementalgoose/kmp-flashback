package tmg.flashback.feature.rss.mapper

import io.ktor.http.Url
import tmg.flashback.feature.rss.extensions.stripHTTP
import tmg.flashback.feature.rss.extensions.stripWWW
import tmg.flashback.feature.rss.models.ArticleSource
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.RssRepository

interface SupportedSourceMapper {
    operator fun invoke(rssLink: String?): ArticleSource?
}

internal class SupportedSourceMapperImpl(
    private val rssRepository: RssRepository
): SupportedSourceMapper {

    private val fallbackUrls: Map<String, String> = mapOf(
        "bbc.co.uk" to "https://www.bbc.co.uk",
        "f1i.com" to "https://en.f1i.com"
    )

    private val all
        get() = rssRepository
            .supportedSources
            .sortedBy { it.rssLink.stripHTTP() }

    override fun invoke(link: String?): ArticleSource? {
        val supportedSource = try {
            val url = Url(link ?: "")

            all.firstOrNull {
                val supportUrl = Url(it.rssLink)
                url.host.stripWWW() == supportUrl.host.stripWWW()
            } ?: getSupportedSourceByFallbackDomain(url)
        } catch (exception: Exception) {
            null
        }

        if (supportedSource == null) {
            return null
        }
        return ArticleSource(
            title = supportedSource.title,
            colour = supportedSource.colour,
            textColor = supportedSource.textColour,
            rssLink = supportedSource.rssLink,
            source = supportedSource.source,
            shortSource = supportedSource.sourceShort,
            contactLink = supportedSource.contactLink
        )
    }

    private fun getSupportedSourceByFallbackDomain(url: Url): SupportedSource? {
        val host = url.host.stripWWW()
        val newHostToCheck = fallbackUrls
            .toList()
            .firstOrNull { (override, _) ->
                override == host
            }
            ?.second

        if (newHostToCheck != null) {
            return all
                .firstOrNull {
                    it.source == newHostToCheck
                }
        }
        return null
    }
}
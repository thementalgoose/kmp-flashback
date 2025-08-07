package tmg.flashback.feature.rss.usecases

import io.ktor.http.URLBuilder
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.RssRepository

internal class GetSourcesUseCaseImpl(
    private val rssRepository: RssRepository
): GetSourcesUseCase {
    override fun invoke(): List<SupportedSource> {
        val urls = rssRepository.rssUrls
        val supported = rssRepository.supportedSources

        val custom = urls.filter { url -> supported.none { it.rssLink == url } }
        return supported + custom.mapNotNull {
            try {
                val urlBuilder = URLBuilder(it).build()
                SupportedSource(
                    rssLink = it,
                    sourceShort = urlBuilder.host.take(3),
                    source = urlBuilder.host,
                    colour = "#0274D1",
                    textColour = "#ffffff",
                    title = urlBuilder.host,
                    contactLink = urlBuilder.host,
                )
            } catch (e: Exception) {
                null
            }
        }
    }

}
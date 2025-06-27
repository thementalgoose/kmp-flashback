package tmg.flashback.feature.rss.repositories.mapper

import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.repositories.model.SupportedSourceJson
import tmg.flashback.feature.rss.repositories.model.SupportedSourcesJson

internal fun SupportedSourcesJson.convert(): List<SupportedSource> {
    return this.sources?.mapNotNull { it.convert() } ?: emptyList()
}

internal fun SupportedSourceJson.convert(): SupportedSource? {
    if (this.source == null || this.title == null || this.rssLink == null || this.colour == null || this.textColour == null) {
        return null
    }

    return SupportedSource(
        rssLink = this.rssLink,
        sourceShort = this.sourceShort ?: this.source.take(2),
        source = this.source,
        colour = this.colour,
        textColour = this.textColour,
        title = this.title,
        contactLink = this.contactLink ?: this.source,
    )
}
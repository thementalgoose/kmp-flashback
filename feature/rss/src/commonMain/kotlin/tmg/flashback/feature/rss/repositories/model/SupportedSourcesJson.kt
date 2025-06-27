package tmg.flashback.feature.rss.repositories.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SupportedSourcesJson(
    @SerialName("sources")
    val sources: List<SupportedSourceJson>? = null
)

@Serializable
internal data class SupportedSourceJson(
    @SerialName("rssLink")
    val rssLink: String? = null,
    @SerialName("sourceShort")
    val sourceShort: String? = null,
    @SerialName("source")
    val source: String? = null,
    @SerialName("colour")
    val colour: String? = null,
    @SerialName("textColour")
    val textColour: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("contactLink")
    val contactLink: String? = null
)
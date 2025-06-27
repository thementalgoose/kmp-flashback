package tmg.flashback.network.rss.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RssXMLModel(
    @SerialName("channel")
    val channel: RssXMLModelChannel?
)

@Serializable
data class RssXMLModelChannel(
    @SerialName("title")
    var title: String? = null,
    @SerialName("link")
    var link: String? = null,
    @SerialName("item")
    var item: List<RssXMLModelItem?>? = null
)

data class RssXMLModelItem(
    @SerialName("title")
    var title: String? = null,
    @SerialName("description")
    var description: String? = null,
    @SerialName("link")
    var link: String? = null,
    @SerialName("pubDate")
    var pubDate: String? = null
)
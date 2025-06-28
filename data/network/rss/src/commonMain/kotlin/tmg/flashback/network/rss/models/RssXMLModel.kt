package tmg.flashback.network.rss.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("rss")
data class RssXMLModel(
    @SerialName("channel")
    val channel: RssXMLModelChannel?
)

@Serializable
@XmlSerialName("channel")
data class RssXMLModelChannel(
    @SerialName("title")
    var title: String? = null,
    @SerialName("link")
    var link: String? = null,
    @SerialName("item")
    var item: List<RssXMLModelItem?>? = null
)

@Serializable
@XmlSerialName("item")
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
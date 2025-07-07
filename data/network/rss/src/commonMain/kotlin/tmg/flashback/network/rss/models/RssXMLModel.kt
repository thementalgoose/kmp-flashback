package tmg.flashback.network.rss.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlOtherAttributes
import nl.adaptivity.xmlutil.serialization.XmlValue // If needed for mixed content

// Define the namespace URI for "dc"
object DublinCore {
    const val NAMESPACE_URI = "https://purl.org/dc/elements/1.1/"
}

@Serializable
@XmlSerialName("rss") // Root element
data class RssFeed(
    @XmlOtherAttributes
    val version: String, // e.g., "2.0"

    // No need to explicitly model xmlns:dc as it's a namespace declaration
    // It will be handled by @XmlSerialName on the specific element (dc:creator)

    @XmlElement(true) // The <channel> element
    val channel: Channel?
)

@Serializable
@XmlSerialName("channel")
data class Channel(
    @XmlElement(true)
    val title: String?,

    @XmlElement(true)
    val link: String?,

    @XmlElement(true)
    val description: String?,

    @XmlElement(true)
    val image: Image? = null,

    @XmlElement(true)
    val pubDate: String? = null,

    // List of <item> elements.
    // kotlinx-serialization-xml handles lists by default if the element name matches.
    @XmlElement(true)
    val item: List<Item> = emptyList() // Default to empty list if no items
)

@Serializable
@XmlSerialName("image")
data class Image(
    @XmlElement(true)
    val title: String?,

    @XmlElement(true)
    val link: String?,

    @XmlElement(true)
    val url: String?
)

@Serializable
@XmlSerialName("item")
data class Item(
    @XmlElement(true)
    val title: String?,

    @XmlElement(true)
    val description: String?,

    @XmlElement(true)
    val link: String?,

    @XmlElement(true)
    val pubDate: String? = null,

    // For namespaced elements:
    // Specify the namespace URI and the local name of the element.
    @XmlElement(true)
    @XmlSerialName("creator", namespace = DublinCore.NAMESPACE_URI, prefix = "dc")
    val creator: String? = null, // dc:creator - make it nullable if it might be missing

    @XmlElement(true)
    val guid: Guid?
)

@Serializable
@XmlSerialName("guid")
data class Guid(
    @XmlOtherAttributes
    val isPermaLink: Boolean? = null, // e.g., "true" - can be String too if you prefer

    @XmlValue // The text content of the <guid> element
    val value: String?
)


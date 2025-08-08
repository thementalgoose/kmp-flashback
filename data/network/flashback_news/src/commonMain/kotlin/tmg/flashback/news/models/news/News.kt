package tmg.flashback.news.models.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    @SerialName("message")
    val message: String,
    @SerialName("url")
    val url: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("dateAdded")
    val dateAdded: String,
)
package tmg.flashback.news.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetadataWrapper<T>(
    @SerialName("lastUpdated")
    val lastUpdated: Long,
    @SerialName("data")
    val data: T
)
package tmg.flashback.flashbackapi.api.models.overview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("label")
    val label: String,
    @SerialName("date")
    val date: String,
    @SerialName("type")
    val type: String
) {
    companion object
}
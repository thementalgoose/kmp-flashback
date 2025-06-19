package tmg.flashback.flashbackapi.api.models.circuits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("lat")
    val lat: String,
    @SerialName("lng")
    val lng: String
) {
    companion object
}
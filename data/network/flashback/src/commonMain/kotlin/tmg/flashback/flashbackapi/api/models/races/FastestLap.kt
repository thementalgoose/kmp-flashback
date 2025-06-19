package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FastestLap(
    @SerialName("position")
    val position: Int,
    @SerialName("time")
    val time: String
) {
    companion object
}
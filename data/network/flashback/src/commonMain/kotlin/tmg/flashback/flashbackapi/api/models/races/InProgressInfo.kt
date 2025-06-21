package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InProgressInfo(
    @SerialName("name")
    val name: String,
    @SerialName("round")
    val round: Int
) {
    companion object
}
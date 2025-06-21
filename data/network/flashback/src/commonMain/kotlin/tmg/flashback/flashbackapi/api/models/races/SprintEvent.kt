package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SprintEvent(
    @SerialName("qualifying")
    val qualifying: Map<String, SprintQualifyingResult>?,
    @SerialName("race")
    val race: Map<String, SprintRaceResult>?,
) {
    companion object
}
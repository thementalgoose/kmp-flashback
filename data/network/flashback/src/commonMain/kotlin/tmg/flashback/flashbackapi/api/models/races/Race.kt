package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.overview.Schedule

@Serializable
data class Race(
    @SerialName("data")
    val data: RaceData,
    @SerialName("race")
    val race: Map<String, RaceResult>? = null,
    @SerialName("sprintEvent")
    val sprintEvent: SprintEvent? = null,
    @SerialName("qualifying")
    val qualifying: Map<String, QualifyingResult>? = null,
    @SerialName("schedule")
    val schedule: List<Schedule>? = null,
) {
    companion object
}
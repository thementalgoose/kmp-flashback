package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.overview.Schedule

@Serializable
data class Round(
    @SerialName("drivers")
    val drivers: Map<String, Driver>,
    @SerialName("constructors")
    val constructors: Map<String, Constructor>,
    @SerialName("data")
    val data: RaceData,
    @SerialName("race")
    val race: Map<String, RaceResult>?,
    @SerialName("sprintEvent")
    val sprintEvent: SprintEvent? = null,
    @SerialName("qualifying")
    val qualifying: Map<String, QualifyingResult>?,
    @SerialName("schedule")
    val schedule: List<Schedule>? = null
) {
    companion object
}
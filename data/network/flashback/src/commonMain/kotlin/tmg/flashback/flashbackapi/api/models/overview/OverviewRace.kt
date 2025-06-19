package tmg.flashback.flashbackapi.api.models.overview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.circuits.Circuit

typealias Overview = Map<String, OverviewRace>

@Serializable
data class OverviewRace(
    @SerialName("season")
    val season: Int,
    @SerialName("round")
    val round: Int,
    @SerialName("name")
    val name: String,
    @SerialName("circuit")
    val circuit: Circuit,
    @SerialName("date")
    val date: String,
    @SerialName("laps")
    val laps: String? = null,
    @SerialName("time")
    val time: String? = null,
    @SerialName("hasQualifying")
    val hasQualifying: Boolean,
    @SerialName("hasSprint")
    val hasSprint: Boolean,
    @SerialName("hasRace")
    val hasRace: Boolean,
    @SerialName("schedule")
    val schedule: List<Schedule>? = null,
) {
    companion object
}
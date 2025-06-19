package tmg.flashback.flashbackapi.api.models.drivers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.races.RaceData

@Serializable
data class DriverHistory(
    @SerialName("driver")
    val driver: Driver,
    @SerialName("standings")
    val standings: Map<String, DriverHistoryStanding>
) {
    companion object
}

@Serializable
data class DriverHistoryStanding(
    @SerialName("season")
    val season: Int,
    @SerialName("championshipPosition")
    val championshipPosition: Int? = null,
    @SerialName("points")
    val points: Double,
    @SerialName("inProgress")
    val inProgress: Boolean,
    @SerialName("races")
    val races: Map<String, DriverHistoryStandingRace>
) {
    companion object
}

@Serializable
data class DriverHistoryStandingRace(
    @SerialName("constructor")
    val constructor: Constructor,
    @SerialName("race")
    val race: RaceData,
    @SerialName("sprint")
    val sprint: DriverHistoryStandingRaceSprint? = null,
    @SerialName("qualified")
    val qualified: Int?,
    @SerialName("gridPos")
    val gridPos: Int?,
    @SerialName("finished")
    val finished: Int,
    @SerialName("status")
    val status: String,
    @SerialName("points")
    val points: Double
) {
    companion object
}

@Serializable
data class DriverHistoryStandingRaceSprint(
    @SerialName("qualifying")
    val qualifying: Boolean = false,
    @SerialName("race")
    val race: Boolean = false
) {
    companion object
}
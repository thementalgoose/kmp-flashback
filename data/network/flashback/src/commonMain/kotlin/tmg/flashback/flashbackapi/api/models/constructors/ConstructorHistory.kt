package tmg.flashback.flashbackapi.api.models.constructors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.drivers.Driver

@Serializable
data class ConstructorHistory(
    @SerialName("constructor")
    val constructor: Constructor,
    @SerialName("standings")
    val standings: Map<String, ConstructorHistoryStanding>
) {
    companion object
}

@Serializable
data class ConstructorHistoryStanding(
    @SerialName("season")
    val season: Int,
    @SerialName("championshipPosition")
    val championshipPosition: Int? = null,
    @SerialName("points")
    val points: Double? = 0.0,
    @SerialName("inProgress")
    val inProgress: Boolean,
    @SerialName("races")
    val races: Int? = 0,
    @SerialName("drivers")
    val drivers: Map<String, ConstructorHistoryStandingDriver>
) {
    companion object
}

@Serializable
data class ConstructorHistoryStandingDriver(
    @SerialName("driver")
    val driver: Driver,
    @SerialName("points")
    val points: Double,
    @SerialName("wins")
    val wins: Int? = null,
    @SerialName("races")
    val races: Int? = null,
    @SerialName("podiums")
    val podiums: Int? = null,
    @SerialName("pointsFinishes")
    val pointsFinishes: Int? = null,
    @SerialName("pole")
    val pole: Int? = null,
    @SerialName("championshipPosition")
    val championshipPosition: Int? = null
) {
    companion object
}
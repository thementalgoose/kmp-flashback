package tmg.flashback.flashbackapi.api.models.lineup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver

@Serializable
data class Lineup(
    @SerialName("drivers")
    val drivers: Map<String, Driver>,
    @SerialName("constructors")
    val constructors: Map<String, Constructor>,
    @SerialName("lineup")
    val lineup: Map<String, LineupSeason>
) {
    companion object
}
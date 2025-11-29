package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver

@Serializable
data class Standings(
    @SerialName("season")
    val season: Int,
    @SerialName("driverStandings")
    val driverStandings: Map<String, DriverStandings>?,
    @SerialName("constructorStandings")
    val constructorStandings: Map<String, ConstructorStandings>?,
    @SerialName("drivers")
    val drivers: Map<String, Driver>,
    @SerialName("constructors")
    val constructors: Map<String, Constructor>
) {
    companion object
}
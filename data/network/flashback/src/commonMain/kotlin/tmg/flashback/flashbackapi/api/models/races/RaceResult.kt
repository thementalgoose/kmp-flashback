package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceResult(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("driverNumber")
    val driverNumber: String,
    @SerialName("constructorId")
    val constructorId: String,
    @SerialName("points")
    val points: Double,
    @SerialName("qualified")
    val qualified: Int? = null,
    @SerialName("gridPos")
    val gridPos: Int? = null,
    @SerialName("finished")
    val finished: Int,
    @SerialName("status")
    val status: String,
    @SerialName("time")
    val time: String? = null,
    @SerialName("fastestLap")
    val fastestLap: FastestLap?
) {
    companion object
}
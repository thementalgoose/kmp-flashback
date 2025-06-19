package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SprintRaceResult(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("driverNumber")
    val driverNumber: String,
    @SerialName("constructorId")
    val constructorId: String,
    @SerialName("points")
    val points: Double,
    @SerialName("gridPos")
    val gridPos: Int? = null,
    @SerialName("finished")
    val finished: Int,
    @SerialName("status")
    val status: String,
    @SerialName("time")
    val time: String? = null
) {
    companion object
}
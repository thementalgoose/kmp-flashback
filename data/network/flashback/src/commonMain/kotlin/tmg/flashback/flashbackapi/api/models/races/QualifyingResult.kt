package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QualifyingResult(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("driverNumber")
    val driverNumber: String?,
    @SerialName("constructorId")
    val constructorId: String,
    @SerialName("points")
    val points: Double? = null,
    @SerialName("qualified")
    val qualified: Int,
    @SerialName("q1")
    val q1: String? = null,
    @SerialName("q2")
    val q2: String? = null,
    @SerialName("q3")
    val q3: String? = null
) {
    companion object
}
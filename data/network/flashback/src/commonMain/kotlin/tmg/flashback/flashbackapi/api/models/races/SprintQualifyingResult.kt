package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SprintQualifyingResult(
    @SerialName("driverId")
    val driverId: String,
    @SerialName("driverNumber")
    val driverNumber: String?,
    @SerialName("constructorId")
    val constructorId: String,
    @SerialName("qualified")
    val qualified: Int,
    @SerialName("sq1")
    val sq1: String? = null,
    @SerialName("sq2")
    val sq2: String? = null,
    @SerialName("sq3")
    val sq3: String? = null
)
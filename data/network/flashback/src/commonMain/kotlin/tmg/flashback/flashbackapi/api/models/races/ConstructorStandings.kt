package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorStandings(
    @SerialName("constructorId")
    val constructorId: String,
    @SerialName("points")
    val points: Double,
    @SerialName("inProgress")
    val inProgress: Boolean? = null,
    @SerialName("inProgressInfo")
    val inProgressInfo: InProgressInfo? = null,
    @SerialName("races")
    val races: Int,
    @SerialName("position")
    val position: Int? = null,
    @SerialName("drivers")
    val drivers: Map<String, Double>
) {
    companion object
}
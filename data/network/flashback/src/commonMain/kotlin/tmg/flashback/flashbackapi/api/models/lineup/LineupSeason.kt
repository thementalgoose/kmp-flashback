package tmg.flashback.flashbackapi.api.models.lineup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineupSeason(
    @SerialName("season")
    val season: Int,
    @SerialName("drivers")
    val drivers: Map<String, String>,
) {
    companion object
}
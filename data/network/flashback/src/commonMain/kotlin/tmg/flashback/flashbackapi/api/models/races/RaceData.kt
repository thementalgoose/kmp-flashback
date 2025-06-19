package tmg.flashback.flashbackapi.api.models.races

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.circuits.Circuit

@Serializable
data class RaceData(
    @SerialName("season")
    val season: Int,
    @SerialName("round")
    val round: Int,
    @SerialName("name")
    val name: String,
    @SerialName("date")
    val date: String,
    @SerialName("laps")
    val laps: String? = null,
    @SerialName("time")
    val time: String? = null,
    @SerialName("circuit")
    val circuit: Circuit,
    @SerialName("wikiUrl")
    val wikiUrl: String? = null,
    @SerialName("youtubeUrl")
    val youtubeUrl: String? = null
) {
    companion object
}
package tmg.flashback.flashbackapi.api.models.circuits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver

@Serializable
data class CircuitHistory(
    @SerialName("data")
    val data: Circuit,
    @SerialName("results")
    val results: Map<String, CircuitResult>?
) {
    companion object
}

@Serializable
data class CircuitResult(
    @SerialName("race")
    val race: CircuitResultRace,
    @SerialName("preview")
    val preview: List<CircuitPreviewPosition>?
) {
    companion object
}

@Serializable
data class CircuitResultRace(
    @SerialName("season")
    val season: Int,
    @SerialName("round")
    val round: Int,
    @SerialName("name")
    val name: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String? = null,
    @SerialName("wikiUrl")
    val wikiUrl: String? = null,
) {
    companion object
}

@Serializable
data class CircuitPreviewPosition(
    @SerialName("position")
    val position: Int,
    @SerialName("driver")
    val driver: Driver,
    @SerialName("constructor")
    val constructor: Constructor
) {
    companion object
}
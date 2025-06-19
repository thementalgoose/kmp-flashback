package tmg.flashback.flashbackapi.api.models.overview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleWeather(
    @SerialName("rainPercent")
    val rainPercent: Double,
    @SerialName("windMs")
    val windMs: Double,
    @SerialName("windBearing")
    val windBearing: Int,
    @SerialName("tempMaxC")
    val tempMaxC: Double,
    @SerialName("tempMinC")
    val tempMinC: Double,
    @SerialName("summary")
    val summary: List<String>
) {
    companion object
}
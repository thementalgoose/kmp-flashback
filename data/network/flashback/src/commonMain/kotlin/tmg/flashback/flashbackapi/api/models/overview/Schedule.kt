package tmg.flashback.flashbackapi.api.models.overview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    @SerialName("label")
    val label: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("weather")
    val weather: ScheduleWeather?
) {
    companion object
}
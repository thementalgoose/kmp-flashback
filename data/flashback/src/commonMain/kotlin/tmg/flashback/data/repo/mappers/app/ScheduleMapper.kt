package tmg.flashback.data.repo.mappers.app

import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.ScheduleWeather
import tmg.flashback.formula1.model.WeatherType

class ScheduleMapper() {

    fun mapSchedule(schedule: tmg.flashback.persistence.flashback.models.overview.Schedule?): Schedule? {
        if (schedule == null) return null
        val date = fromDate(schedule.date)
        val time = fromTime(schedule.time)

        if (date != null && time != null) {
            return Schedule(
                label = schedule.label,
                date = date,
                time = time,
                weather = mapScheduleWeather(schedule)
            )
        }
        return null
    }

    private fun mapScheduleWeather(schedule: tmg.flashback.persistence.flashback.models.overview.Schedule?): ScheduleWeather? {
        return ScheduleWeather(
            rainPercent = schedule?.rainPercent ?: return null,
            tempMinC = schedule.tempMinC ?: return null,
            tempMaxC = schedule.tempMaxC ?: return null,
            windMs = schedule.windMs ?: return null,
            windBearing = schedule.windBearing ?: return null,
            summary = (schedule.summary ?: "")
                .split("|")
                .mapNotNull { it.toWeatherType() }
        )
    }

    private val WeatherType.expectedKey: String
        get() = when (this) {
            WeatherType.CLEAR_SKY -> "clear_sky"
            WeatherType.CLOUDS_LIGHT -> "clouds_light"
            WeatherType.CLOUDS_SCATTERED -> "clouds_scattered"
            WeatherType.CLOUDS_BROKEN -> "clouds_broken"
            WeatherType.CLOUDS_OVERCAST -> "clouds_overcast"
            WeatherType.RAIN_LIGHT -> "rain_light"
            WeatherType.RAIN_MODERATE -> "rain_moderate"
            WeatherType.RAIN_HEAVY -> "rain_heavy"
            WeatherType.THUNDERSTORM -> "thunderstorm"
            WeatherType.SNOW -> "snow"
            WeatherType.SAND -> "sand"
            WeatherType.FOG -> "fog"
            WeatherType.MIST -> "mist"
        }
    private fun String.toWeatherType(): WeatherType? {
        return WeatherType.values().firstOrNull { it.expectedKey == this }
    }
}
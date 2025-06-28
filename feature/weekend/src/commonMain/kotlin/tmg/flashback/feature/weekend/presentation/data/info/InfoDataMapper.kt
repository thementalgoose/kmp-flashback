package tmg.flashback.feature.weekend.presentation.data.info

import kotlinx.datetime.LocalDate
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import tmg.flashback.formula1.model.Race
import tmg.flashback.formula1.model.Schedule

interface InfoDataMapper {
    operator fun invoke(race: Race): InfoModel
}
internal class InfoDataMapperImpl(
    private val weatherRepository: WeatherRepository
): InfoDataMapper {
    override fun invoke(race: Race): InfoModel {
        return InfoModel(
            season = race.raceInfo.season,
            round = race.raceInfo.round,
            raceName = race.raceInfo.name,
            circuit = race.raceInfo.circuit,
            date = race.raceInfo.date,
            time = race.raceInfo.time,
            laps = race.raceInfo.laps,
            youtubeUrl = race.raceInfo.youtube,
            wikipediaUrl = race.raceInfo.wikipediaUrl,
            days = race.getScheduleGrouping(),
            temperatureMetric = weatherRepository.weatherTemperatureMetric,
            windspeedMetric = weatherRepository.weatherWindspeedMetric
        )
    }

    private fun Race.getScheduleGrouping(): List<Pair<LocalDate, List<Pair<Schedule, Boolean>>>> {
        val dayGroupings = this.schedule
            .groupBy { it.timestamp.deviceLocalDateTime.date }
            .toMap()

        if (dayGroupings.isEmpty()) {
            return emptyList()
        }
        return dayGroupings.map { (date, schedules) ->
            date to schedules
                .sortedBy { it.timestamp.utcLocalDateTime }
                .map {
                    val notificationsEnabled = false
                    Pair(it, notificationsEnabled)
                }
        }
    }
}
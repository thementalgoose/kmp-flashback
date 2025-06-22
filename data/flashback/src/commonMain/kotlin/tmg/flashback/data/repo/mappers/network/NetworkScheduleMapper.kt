package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.overview.Schedule
import tmg.flashback.flashbackapi.api.models.overview.OverviewRace
import tmg.flashback.flashbackapi.api.models.races.Race

class NetworkScheduleMapper {

    @Throws(RuntimeException::class)
    fun mapSchedules(race: OverviewRace): List<Schedule> {
        return race.schedule
            ?.map { mapSchedule(race.season, race.round, it) }
            ?.sortedBy { it.time }
            ?.sortedBy { it.date }
            ?: emptyList()
    }

    @Throws(RuntimeException::class)
    fun mapSchedules(race: Race): List<Schedule> {
        return race.schedule
            ?.map { mapSchedule(race.data.season, race.data.round, it) }
            ?.sortedBy { it.time }
            ?.sortedBy { it.date }
            ?: emptyList()
    }

    @Throws(RuntimeException::class)
    fun mapSchedule(season: Int, round: Int, data: tmg.flashback.flashbackapi.api.models.overview.Schedule): Schedule {
        return Schedule(
            season = season,
            round = round,
            label = data.label,
            date = data.date,
            time = data.time,
            rainPercent = data.weather?.rainPercent,
            tempMaxC = data.weather?.tempMaxC,
            tempMinC = data.weather?.tempMinC,
            windMs = data.weather?.windMs,
            windBearing = data.weather?.windBearing,
            summary = data.weather?.summary?.joinToString(separator = "|")
        )
    }
}
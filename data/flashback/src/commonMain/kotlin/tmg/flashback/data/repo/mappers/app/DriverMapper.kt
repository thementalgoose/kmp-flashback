package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonRaceWithConstructor
import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonWithRaces
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.DriverHistorySeason
import tmg.flashback.formula1.model.DriverHistorySeasonRace

class DriverMapper(
    private val driverDataMapper: DriverDataMapper,
    private val constructorDataMapper: ConstructorDataMapper,
    private val raceDataMapper: RaceMapper
) {

    fun mapDriver(history: tmg.flashback.persistence.flashback.models.drivers.DriverHistory?): DriverHistory? {
        if (history == null) return null
        return DriverHistory(
            driver = driverDataMapper.mapDriver(history.driver),
            standings = history.seasons.map {
                mapDriverSeasonWithRaces(it)
            }
        )
    }

    private fun mapDriverSeasonWithRaces(data: DriverSeasonWithRaces): DriverHistorySeason {
        return DriverHistorySeason(
            championshipStanding = data.driverSeason.championshipStanding,
            isInProgress = data.driverSeason.inProgress,
            points = data.driverSeason.points,
            season = data.driverSeason.season,
            constructors = data.races
                .map { it.constructor }
                .toSet()
                .map { constructorDataMapper.mapConstructorData(it) },
            raceOverview = data.races.map { mapDriverSeasonRace(it) }
        )
    }

    private fun mapDriverSeasonRace(data: DriverSeasonRaceWithConstructor): DriverHistorySeasonRace {
        return DriverHistorySeasonRace(
            isSprint = data.race.sprintRace || data.race.sprintQualifying,
            status = RaceStatus.from(data.race.status),
            finished = data.race.finished,
            points = data.race.points,
            qualified = data.race.qualified,
            gridPos = data.race.gridPos,
            raceInfo = raceDataMapper.mapRaceInfoWithCircuit(data.round),
            constructor = constructorDataMapper.mapConstructorData(data.constructor),
        )
    }
}
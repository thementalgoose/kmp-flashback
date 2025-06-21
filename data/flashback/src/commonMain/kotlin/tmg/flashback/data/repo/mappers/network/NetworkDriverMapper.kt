package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.drivers.DriverSeason
import tmg.flashback.persistence.flashback.models.drivers.DriverSeasonRace
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistoryStanding
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistoryStandingRace

class NetworkDriverMapper {

    @Throws(RuntimeException::class)
    fun mapDriverSeason(driverId: String, driver: DriverHistoryStanding): DriverSeason {
        return DriverSeason(
            driverId = driverId,
            season = driver.season,
            championshipStanding = driver.championshipPosition,
            points = driver.points,
            inProgress = driver.inProgress
        )
    }

    @Throws(RuntimeException::class)
    fun mapDriverSeasonRace(driverId: String, season: Int, data: DriverHistoryStandingRace): DriverSeasonRace {
        return DriverSeasonRace(
            driverId = driverId,
            season = season,
            round = data.race.round,
            constructorId = data.constructor.id,
            sprintQualifying = data.sprint?.qualifying ?: false,
            sprintRace = data.sprint?.race ?: false,
            qualified = data.qualified,
            gridPos = data.gridPos,
            finished = data.finished,
            status = data.status,
            points = data.points,
        )
    }
}
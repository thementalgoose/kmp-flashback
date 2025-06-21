package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingDriverWithDriver
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingWithDrivers
import tmg.flashback.formula1.model.SeasonConstructorStandingSeason
import tmg.flashback.formula1.model.SeasonConstructorStandingSeasonDriver
import tmg.flashback.formula1.model.SeasonConstructorStandings

class ConstructorStandingMapper(
    private val driverDataMapper: DriverDataMapper,
    private val constructorDataMapper: ConstructorDataMapper
) {

    fun mapConstructorStanding(list: List<ConstructorStandingWithDrivers>): SeasonConstructorStandings? {
        if (list.isEmpty()) return null
        return SeasonConstructorStandings(
            standings = list
                .map { mapConstructorStanding(it) }
                .sortedBy { it.championshipPosition ?: Int.MAX_VALUE }
        )
    }

    fun mapConstructorStanding(data: ConstructorStandingWithDrivers): SeasonConstructorStandingSeason {
        return SeasonConstructorStandingSeason(
            season = data.standing.season,
            constructor = constructorDataMapper.mapConstructorData(data.constructor),
            points = data.standing.points,
            inProgress = data.standing.inProgress,
            inProgressName = data.standing.inProgressName,
            inProgressRound = data.standing.inProgressRound,
            races = data.standing.races,
            championshipPosition = data.standing.position,
            drivers = data.drivers
                .sortedByDescending { it.standing.points }
                .map {
                    mapConstructorStandingDriver(it)
                }
        )
    }

    private fun mapConstructorStandingDriver(data: ConstructorStandingDriverWithDriver): SeasonConstructorStandingSeasonDriver {
        return SeasonConstructorStandingSeasonDriver(
            points = data.standing.points,
            driver = driverDataMapper.mapDriver(data.driver)
        )
    }
}
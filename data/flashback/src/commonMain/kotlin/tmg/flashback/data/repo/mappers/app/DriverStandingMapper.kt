package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.standings.DriverStandingConstructorWithConstructor
import tmg.flashback.persistence.flashback.models.standings.DriverStandingWithConstructors
import tmg.flashback.formula1.model.SeasonDriverStandingSeason
import tmg.flashback.formula1.model.SeasonDriverStandingSeasonConstructor
import tmg.flashback.formula1.model.SeasonDriverStandings

class DriverStandingMapper(
    private val driverDataMapper: DriverDataMapper,
    private val constructorDataMapper: ConstructorDataMapper
) {

    fun mapDriverStanding(list: List<DriverStandingWithConstructors>): SeasonDriverStandings? {
        if (list.isEmpty()) return null
        return SeasonDriverStandings(
            standings = list
                .map { mapDriverStanding(it) }
                .sortedBy { it.championshipPosition ?: Int.MAX_VALUE }
        )
    }

    fun mapDriverStanding(data: DriverStandingWithConstructors): SeasonDriverStandingSeason {
        return SeasonDriverStandingSeason(
            season = data.standing.season,
            driver = driverDataMapper.mapDriver(data.driver),
            points = data.standing.points,
            inProgress = data.standing.inProgress,
            inProgressName = data.standing.inProgressName,
            inProgressRound = data.standing.inProgressRound,
            races = data.standing.races,
            championshipPosition = data.standing.position,
            constructors = data.constructors.map {
                mapDriverStandingConstructor(it)
            },
        )
    }

    private fun mapDriverStandingConstructor(data: DriverStandingConstructorWithConstructor): SeasonDriverStandingSeasonConstructor {
        return SeasonDriverStandingSeasonConstructor(
            points = data.standing.points,
            constructor = constructorDataMapper.mapConstructorData(data.constructor)
        )
    }
}
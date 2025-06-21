package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.ConstructorSeasonDriverWithDriver
import tmg.flashback.persistence.flashback.models.constructors.ConstructorSeasonWithDrivers
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.ConstructorHistorySeason
import tmg.flashback.formula1.model.ConstructorHistorySeasonDriver
import tmg.flashback.formula1.model.DriverEntry

class ConstructorMapper(
    private val constructorDataMapper: ConstructorDataMapper,
    private val driverDataMapper: DriverDataMapper
) {

    fun mapConstructor(history: tmg.flashback.persistence.flashback.models.constructors.ConstructorHistory?): ConstructorHistory? {
        if (history == null) return null
        return ConstructorHistory(
            constructor = constructorDataMapper.mapConstructorData(history.constructor),
            standings = history.seasons.map {
                mapConstructorSeason(history.constructor, it)
            }
        )
    }

    private fun mapConstructorSeason(constructor: Constructor, data: ConstructorSeasonWithDrivers): ConstructorHistorySeason {
        return ConstructorHistorySeason(
            isInProgress = data.constructorSeason.inProgress,
            championshipStanding = data.constructorSeason.championshipStanding,
            points = data.constructorSeason.points,
            season = data.constructorSeason.season,
            races = data.constructorSeason.races,
            drivers = data.drivers.associate {
                it.driver.id to mapConstructorSeasonDriver(constructor, it)
            }
        )
    }

    private fun mapConstructorSeasonDriver(constructor: Constructor, data: ConstructorSeasonDriverWithDriver): ConstructorHistorySeasonDriver {
        return ConstructorHistorySeasonDriver(
            driver = DriverEntry(
                driver = driverDataMapper.mapDriver(data.driver),
                constructor = constructorDataMapper.mapConstructorData(constructor)
            ),
            points = data.results.points,
            wins = data.results.wins,
            races = data.results.races,
            podiums = data.results.podiums,
            finishesInPoints = data.results.pointsFinishes,
            polePosition = data.results.pole,
            championshipStanding = data.results.championshipPosition,
        )
    }
}
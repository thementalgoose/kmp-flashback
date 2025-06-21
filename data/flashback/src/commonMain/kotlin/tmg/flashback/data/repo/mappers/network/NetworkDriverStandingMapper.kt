package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.standings.DriverStanding
import tmg.flashback.persistence.flashback.models.standings.DriverStandingConstructor
import tmg.flashback.flashbackapi.api.models.races.DriverStandings

class NetworkDriverStandingMapper {

    @Throws(RuntimeException::class)
    fun mapDriverStanding(season: Int, data: DriverStandings): DriverStanding {
        return DriverStanding(
            driverId = data.driverId,
            season = season,
            points = data.points,
            position = data.position,
            inProgress = data.inProgress ?: false,
            inProgressName = data.inProgressInfo?.name,
            inProgressRound = data.inProgressInfo?.round,
            races = data.races
        )
    }

    @Throws(RuntimeException::class)
    fun mapDriverStandingConstructor(season: Int, data: DriverStandings): List<DriverStandingConstructor> {
        return data.constructors.map { (constructorId, points) ->
            DriverStandingConstructor(
                constructorId = constructorId,
                season = season,
                driverId = data.driverId,
                points = points,
            )
        }
    }
}
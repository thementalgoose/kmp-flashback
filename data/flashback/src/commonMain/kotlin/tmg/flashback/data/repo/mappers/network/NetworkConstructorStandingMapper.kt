package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.standings.ConstructorStanding
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingDriver
import tmg.flashback.flashbackapi.api.models.races.ConstructorStandings

class NetworkConstructorStandingMapper {

    @Throws(RuntimeException::class)
    fun mapConstructorStanding(season: Int, data: ConstructorStandings): ConstructorStanding {
        return ConstructorStanding(
            constructorId = data.constructorId,
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
    fun mapConstructorStandingDriver(season: Int, data: ConstructorStandings): List<ConstructorStandingDriver> {
        return data.drivers.map { (driverId, points) ->
            ConstructorStandingDriver(
                constructorId = data.constructorId,
                season = season,
                driverId = driverId,
                points = points,
            )
        }
    }
}
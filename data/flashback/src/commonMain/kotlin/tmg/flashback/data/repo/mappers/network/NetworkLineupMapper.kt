package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.lineup.Lineup

class NetworkLineupMapper {

    private fun mapLineup(model: tmg.flashback.flashbackapi.api.models.lineup.LineupSeason): List<Lineup> {
        return model.drivers.map { (driverId, constructorId) ->
            Lineup(
                constructorId = constructorId,
                driverId = driverId,
                season = model.season
            )
        }
    }

    fun mapLineup(model: Map<String, tmg.flashback.flashbackapi.api.models.lineup.LineupSeason>): List<Lineup> {
        return model
            .values
            .flatMap {
                mapLineup(it)
            }
    }
}
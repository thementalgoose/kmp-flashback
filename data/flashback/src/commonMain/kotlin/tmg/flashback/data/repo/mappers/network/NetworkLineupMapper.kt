package tmg.flashback.data.repo.mappers.network

import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.persistence.flashback.models.lineup.Lineup

class NetworkLineupMapper {

    private fun mapLineup(model: tmg.flashback.flashbackapi.api.models.lineup.LineupSeason): List<Lineup> {
        return model.drivers.map { (driverId, constructorId) ->
            logDebug("Saving Lineup $model")
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
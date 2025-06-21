package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.race.RaceInfo
import tmg.flashback.flashbackapi.api.models.races.RaceData

class NetworkRaceDataMapper {

    @Throws(RuntimeException::class)
    fun mapRaceData(raceData: RaceData): RaceInfo {
        return RaceInfo(
            season = raceData.season,
            round = raceData.round,
            name = raceData.name,
            date = raceData.date,
            laps = raceData.laps,
            circuitId = raceData.circuit.id,
            time = raceData.time,
            wikiUrl = raceData.wikiUrl,
            youtube = raceData.youtubeUrl
        )
    }
}
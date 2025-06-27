package tmg.flashback.feature.weekend.presentation.data.info

import tmg.flashback.formula1.model.Race

interface InfoDataMapper {
    operator fun invoke(race: Race): InfoModel
}
internal class InfoDataMapperImpl(): InfoDataMapper {
    override fun invoke(race: Race): InfoModel {
        return InfoModel(
            season = race.raceInfo.season,
            round = race.raceInfo.round,
            raceName = race.raceInfo.name,
            circuit = race.raceInfo.circuit,
            laps = race.raceInfo.laps,
            youtubeUrl = race.raceInfo.youtube,
            wikipediaUrl = race.raceInfo.wikipediaUrl,
            schedule = race.schedule
        )
    }
}
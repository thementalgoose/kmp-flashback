package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.overview.Event
import tmg.flashback.formula1.model.Season

class SeasonMapper(
    private val raceMapper: RaceMapper,
    private val eventMapper: EventMapper
) {
    fun mapSeason(
        season: Int,
        races: List<tmg.flashback.persistence.flashback.models.race.Race>,
        events: List<Event>
    ): Season {
        return Season(
            season = season,
            races = races.mapNotNull { raceMapper.mapRace(it) },
            event = events.mapNotNull { eventMapper.mapEvent(it) }
        )
    }
}
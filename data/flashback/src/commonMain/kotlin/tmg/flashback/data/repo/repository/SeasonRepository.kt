package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.ConstructorStandingMapper
import tmg.flashback.data.repo.mappers.app.DriverStandingMapper
import tmg.flashback.data.repo.mappers.app.SeasonMapper
import tmg.flashback.formula1.model.Season
import tmg.flashback.formula1.model.SeasonConstructorStandings
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface SeasonRepository {
    fun getSeason(season: Int): Flow<Season?>
}

internal class SeasonRepositoryImpl(
    private val flashbackDatabase: FlashbackDatabase,
    private val seasonMapper: SeasonMapper
): SeasonRepository {

    override fun getSeason(season: Int): Flow<Season?> {
        return combine(
            flow = flashbackDatabase.seasonDao().getRaces(season),
            flow2 = flashbackDatabase.eventsDao().getEvents(season),
            transform = { list, winterTesting -> Pair(list, winterTesting) }
        ).map { (list, winterTesting) ->
            seasonMapper.mapSeason(season, list, winterTesting)
        }
    }
}
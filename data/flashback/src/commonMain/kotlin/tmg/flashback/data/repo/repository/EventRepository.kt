package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.EventMapper
import tmg.flashback.data.repo.mappers.network.NetworkEventMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.formula1.model.Event
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface EventRepository {
    suspend fun fetchEvents(season: Int): Response
    fun getEvents(season: Int): Flow<List<Event>>
}

internal class EventRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkEventMapper: NetworkEventMapper,
    private val eventMapper: EventMapper
): EventRepository {

     override suspend fun fetchEvents(season: Int) = api.makeRequest(
        request = { api.getOverviewEvents(season) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val events = data.mapNotNull { networkEventMapper.mapEvent(season, it) }

            persistence.eventsDao().replaceEventsForSeason(season, events)

            return@makeRequest true
        }
     )

    override fun getEvents(season: Int): Flow<List<Event>> {
        return persistence.eventsDao().getEvents(season)
            .map { list ->
                list.mapNotNull { eventMapper.mapEvent(it) }
            }
    }
}
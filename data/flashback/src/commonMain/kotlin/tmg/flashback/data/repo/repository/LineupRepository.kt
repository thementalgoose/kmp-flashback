package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.LineupMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkLineupMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.formula1.model.LineupSeason
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface LineupRepository {
    suspend fun populateLineup(): Response
    fun getLineup(season: Int): Flow<LineupSeason?>
    fun getLineup(): Flow<List<LineupSeason>?>
}

internal class LineupRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkConstructorDataMapper: NetworkConstructorDataMapper,
    private val networkDriverDataMapper: NetworkDriverDataMapper,
    private val networkLineupMapper: NetworkLineupMapper,
    private val lineupMapper: LineupMapper,
): LineupRepository {

    override suspend fun populateLineup() = api.makeRequest(
        request = { api.getLineup() },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            saveConstructors(data.constructors)
            saveDrivers(data.drivers)

            val lineups = networkLineupMapper.mapLineup(data.lineup)
            persistence.lineupDao().replaceLineups(lineups)

            return@makeRequest true
        }
    )

    override fun getLineup(season: Int): Flow<LineupSeason?> {
        return persistence.lineupDao().getLineup()
            .map { lineupMapper.mapLineup(it) }
            .map { all ->
                all?.firstOrNull { it.season == season }
            }
    }

    override fun getLineup(): Flow<List<LineupSeason>?> {
        return persistence.lineupDao().getLineup()
            .map {
                lineupMapper.mapLineup(it)
            }
    }

    private suspend fun saveConstructors(constructors: Map<String, Constructor>?) {
        if (constructors == null) return
        val items = constructors.values
            .map { networkConstructorDataMapper.mapConstructorData(it) }
        persistence.constructorDao().insertAll(items)
    }

    private suspend fun saveDrivers(drivers: Map<String, Driver>?) {
        val items = (drivers?.values ?: emptyList())
            .map { networkDriverDataMapper.mapDriverData(it) }
        persistence.driverDao().insertAll(items)
    }
}
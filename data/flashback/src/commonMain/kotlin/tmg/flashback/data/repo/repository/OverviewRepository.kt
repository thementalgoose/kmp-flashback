package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.OverviewMapper
import tmg.flashback.data.repo.mappers.network.NetworkCircuitDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkOverviewMapper
import tmg.flashback.data.repo.mappers.network.NetworkScheduleMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.formula1.model.Overview
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface OverviewRepository {
    suspend fun populateOverview(): Response
    suspend fun populateOverview(season: Int): Response
    fun getOverview(season: Int, round: Int): Flow<OverviewRace?>
    fun getOverview(season: Int): Flow<Overview?>
}

internal class OverviewRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val overviewMapper: OverviewMapper,
    private val networkOverviewMapper: NetworkOverviewMapper,
    private val networkCircuitDataMapper: NetworkCircuitDataMapper,
    private val networkScheduleMapper: NetworkScheduleMapper
): OverviewRepository {

    /**
     * overview.json
     * Fetch overview data for all seasons currently available
     */
    override suspend fun populateOverview() = api.makeRequest(
        request = { api.getOverview() },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val allCircuits = data.values.map { networkCircuitDataMapper.mapCircuitData(it.circuit) }
            val allOverview = data.values
                .mapNotNull { networkOverviewMapper.mapOverview(it) }
            val scheduled = data.values
                .map { networkScheduleMapper.mapSchedules(it) }
                .flatten()

            persistence.circuitDao().insertCircuit(allCircuits)
            persistence.overviewDao().insertAll(allOverview)
            persistence.scheduleDao().insertAll(scheduled)

            return@makeRequest true
        }
    )

    /**
     * overview/{season}.json
     * Fetch overview data for a specific season [season]
     * @param season
     */
    override suspend fun populateOverview(season: Int) = api.makeRequest(
        request = { api.getOverview(season) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val allCircuits = data.values.map { networkCircuitDataMapper.mapCircuitData(it.circuit) }
            val allOverview = data.values
                .mapNotNull { networkOverviewMapper.mapOverview(it) }
            val scheduled = data.values
                .map { networkScheduleMapper.mapSchedules(it) }
                .flatten()

            persistence.circuitDao().insertCircuit(allCircuits)
            persistence.overviewDao().insertAll(allOverview)
            persistence.scheduleDao().replaceAllForSeason(season, scheduled)

            val maxRoundRemote = allOverview.maxOfOrNull { it.round } ?: 0
            val maxRoundLocal = persistence.overviewDao().getLastRound(season)?.overview?.round ?: 0
            if (maxRoundLocal > maxRoundRemote) {
                // We need to remove old rounds!
                for (round in (maxRoundRemote + 1) until maxRoundLocal + 1) {
                    persistence.overviewDao().deleteOverview(season, round)
                }
            }

            return@makeRequest true
        }
    )

    override fun getOverview(season: Int, round: Int): Flow<OverviewRace?> {
        return persistence.overviewDao().getOverview(season, round)
            .map {
                if (it == null) return@map null
                try {
                    overviewMapper.mapOverview(it)
                } catch (e: RuntimeException) {
                    null
                }
            }
    }

    override fun getOverview(season: Int): Flow<Overview?> {
        return persistence.overviewDao().getOverview(season)
            .map { overview ->
                Overview(
                    season = season,
                    overviewRaces = overview
                        .map { overviewMapper.mapOverview(it) }
                        .sortedBy { it.round }
                )
            }
    }
}
package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.ConstructorDataMapper
import tmg.flashback.data.repo.mappers.app.ConstructorMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface ConstructorRepository {
    suspend fun populateConstructors(): Response
    suspend fun populateConstructor(constructorId: String): Response
    fun getConstructorOverview(id: String): Flow<ConstructorHistory?>
    fun getConstructors(): Flow<List<tmg.flashback.formula1.model.Constructor>>
}

internal class ConstructorRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkDriverDataMapper: NetworkDriverDataMapper,
    private val networkConstructorDataMapper: NetworkConstructorDataMapper,
    private val networkConstructorMapper: NetworkConstructorMapper,
    private val constructorMapper: ConstructorMapper,
    private val constructorDataMapper: ConstructorDataMapper,
): ConstructorRepository {

    /**
     * constructors.json
     * Fetch constructor data for all constructors currently available
     */
    override suspend fun populateConstructors() = api.makeRequest(
        request = { api.getConstructors() },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val allConstructors = data.values.map {
                networkConstructorDataMapper.mapConstructorData(it)
            }
            persistence.constructorDao().insertAll(allConstructors)
            return@makeRequest true
        }
    )

    /**
     * constructors/{constructorId}.json
     * Fetch constructor data and history for a specific constructor [constructorId]
     * @param constructorId
     */
    override suspend fun populateConstructor(constructorId: String) = api.makeRequest(
        request = { api.getConstructor(constructorId) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            saveDrivers(data)

            val constructor = networkConstructorDataMapper.mapConstructorData(data.constructor)
            val allSeasons = data.standings.values
                .map { standing ->
                    networkConstructorMapper.mapConstructorSeason(data.constructor.id, standing)
                }
            val allSeasonDrivers = data.standings.values
                .map { standing -> standing.drivers.values.map { Pair(standing.season, it) } }
                .flatten()
                .map { (season, driver) -> networkConstructorMapper.mapConstructorSeasonDriver(constructor.id, season, driver) }

            persistence.constructorDao().insertConstructor(constructor, allSeasons, allSeasonDrivers)

            return@makeRequest true
        }
    )

    override fun getConstructorOverview(id: String): Flow<ConstructorHistory?> {
        return persistence.constructorDao().getConstructorHistory(id)
            .map { constructorMapper.mapConstructor(it) }
    }

    override fun getConstructors(): Flow<List<tmg.flashback.formula1.model.Constructor>> {
        return persistence.constructorDao().getConstructors()
            .map { list -> list.map { constructorDataMapper.mapConstructorData(it) } }
    }

    private suspend fun saveDrivers(data: tmg.flashback.flashbackapi.api.models.constructors.ConstructorHistory): Boolean {
        val drivers = data.standings.values
            .map { it.drivers.values.map { it.driver } }
            .flatten()
            .toSet()
            .map { networkDriverDataMapper.mapDriverData(it) }
        persistence.driverDao().insertAll(drivers)
        return true
    }
}
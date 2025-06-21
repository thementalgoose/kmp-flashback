package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.DriverDataMapper
import tmg.flashback.data.repo.mappers.app.DriverMapper
import tmg.flashback.data.repo.mappers.network.NetworkCircuitDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverMapper
import tmg.flashback.data.repo.mappers.network.NetworkRaceDataMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface DriverRepository {
    suspend fun populateDrivers(): Response
    suspend fun populateDriver(driverId: String): Response
    fun getDriverOverview(id: String): Flow<DriverHistory?>
    fun getDrivers(): Flow<List<tmg.flashback.formula1.model.Driver>>
}

internal class DriverRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkDriverMapper: NetworkDriverMapper,
    private val networkDriverDataMapper: NetworkDriverDataMapper,
    private val networkConstructorDataMapper: NetworkConstructorDataMapper,
    private val networkCircuitDataMapper: NetworkCircuitDataMapper,
    private val networkRaceDataMapper: NetworkRaceDataMapper,
    private val driverMapper: DriverMapper,
    private val driverDataMapper: DriverDataMapper,
): DriverRepository {

    /**
     * drivers.json
     * Fetch driver data for all drivers currently available
     */
    override suspend fun populateDrivers() = api.makeRequest(
        request = { api.getDrivers() },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val allDrivers = data.values.map {
                networkDriverDataMapper.mapDriverData(it)
            }
            persistence.driverDao().insertAll(allDrivers)
            return@makeRequest true
        }
    )

    /**
     * drivers/{driverId}.json
     * Fetch driver data and history for a specific driver [driverId]
     * @param driverId
     */
    override suspend fun populateDriver(driverId: String) = api.makeRequest(
        request = { api.getDriver(driverId) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            saveConstructors(data)
            saveRaceAndCircuits(data)

            val driver = networkDriverDataMapper.mapDriverData(data.driver)
            val allStandings = data.standings.values
                .map { standing ->
                    networkDriverMapper.mapDriverSeason(data.driver.id, standing)
                }
            val allStandingRaces = data.standings.values
                .map { standing -> standing.races.values.map { Pair(standing.season, it) } }
                .flatten()
                .map { (season, race) -> networkDriverMapper.mapDriverSeasonRace(data.driver.id, season, race) }

            persistence.driverDao().insertDriver(driver, allStandings, allStandingRaces)

            return@makeRequest true
        }
    )

    override fun getDriverOverview(id: String): Flow<DriverHistory?> {
        return persistence.driverDao().getDriverHistory(id)
            .map { driverMapper.mapDriver(it) }
    }

    override fun getDrivers(): Flow<List<tmg.flashback.formula1.model.Driver>> {
        return persistence.driverDao().getDrivers()
            .map { list -> list.map { driverDataMapper.mapDriver(it) } }
    }

    private suspend fun saveConstructors(data: tmg.flashback.flashbackapi.api.models.drivers.DriverHistory): Boolean {
        val constructors = data.standings.values
            .map { it.races.values.map { it.constructor } }
            .flatten()
            .toSet()
            .map { networkConstructorDataMapper.mapConstructorData(it) }
        persistence.constructorDao().insertAll(constructors)
        return true
    }

    private suspend fun saveRaceAndCircuits(data: tmg.flashback.flashbackapi.api.models.drivers.DriverHistory): Boolean {
        val races = data.standings.values
            .map { it.races.values.map { it.race } }
            .flatten()
            .toSet()
        val circuits = races
            .map { it.circuit }
            .toSet()
        persistence.circuitDao().insertCircuit(circuits.map { networkCircuitDataMapper.mapCircuitData(it) })
        persistence.seasonDao().insertRaceData(races.map { networkRaceDataMapper.mapRaceData(it) })
        return true
    }
}
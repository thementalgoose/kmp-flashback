package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.CircuitMapper
import tmg.flashback.data.repo.mappers.network.NetworkCircuitDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkCircuitMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistory
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface CircuitRepository {
    suspend fun populateCircuits(): Response
    suspend fun populateCircuit(circuitId: String): Response
    fun getCircuitHistory(circuitId: String): Flow<CircuitHistory?>
    fun getCircuits(): Flow<List<Circuit>>
    fun getCircuit(circuitId: String): Flow<Circuit?>
}

internal class CircuitRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkCircuitMapper: NetworkCircuitMapper,
    private val networkDriverDataMapper: NetworkDriverDataMapper,
    private val networkConstructorDataMapper: NetworkConstructorDataMapper,
    private val networkCircuitDataMapper: NetworkCircuitDataMapper,
    private val circuitMapper: CircuitMapper,
): CircuitRepository {
    override suspend fun populateCircuits() = api.makeRequest(
        request = { api.getCircuits() },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val allCircuits = data.values
                .map { networkCircuitDataMapper.mapCircuitData(it) }

            persistence.circuitDao().insertCircuit(allCircuits)
            return@makeRequest true
        }
    )

    override suspend fun populateCircuit(circuitId: String) = api.makeRequest(
        request = { api.getCircuit(circuitId) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            val circuit = networkCircuitDataMapper.mapCircuitData(data.data)
            val circuitRounds = (data.results?.values ?: emptyList()).map {
                networkCircuitMapper.mapCircuitRounds(data.data.id, it)
            }

            // Drivers
            val drivers = (data.results?.values ?: emptyList())
                .map {
                    it.preview?.map { it.driver } ?: emptyList()
                }
                .flatten()
                .distinctBy { it.id }
                .map { networkDriverDataMapper.mapDriverData(it) }
            persistence.driverDao().insertAll(drivers)

            // Constructors
            val constructors = (data.results?.values ?: emptyList())
                .map {
                    it.preview?.map { it.constructor } ?: emptyList()
                }
                .flatten()
                .distinctBy { it.id }
                .map { networkConstructorDataMapper.mapConstructorData(it) }
            persistence.constructorDao().insertAll(constructors)

            // Circuit results
            val results = (data.results?.values ?: emptyList())
                .map {
                    networkCircuitMapper.mapCircuitPreviews(it)
                }
                .flatten()

            persistence.circuitDao().insertCircuitRounds(circuitRounds)
            persistence.circuitDao().insertCircuit(listOf(circuit))
            persistence.circuitDao().insertCircuitRoundResults(results)

            return@makeRequest true
        }
    )

    override fun getCircuitHistory(circuitId: String): Flow<CircuitHistory?> {
        return persistence.circuitDao().getCircuitHistory(circuitId)
            .map { model ->
                circuitMapper.mapCircuitHistory(model)
            }
    }

    override fun getCircuits(): Flow<List<Circuit>> {
        return persistence.circuitDao().getCircuits()
            .map { list -> list.mapNotNull { circuitMapper.mapCircuit(it) } }
    }

    override fun getCircuit(circuitId: String): Flow<Circuit?> {
        return persistence.circuitDao().getCircuit(circuitId)
            .map { circuitMapper.mapCircuit(it) }
    }

}
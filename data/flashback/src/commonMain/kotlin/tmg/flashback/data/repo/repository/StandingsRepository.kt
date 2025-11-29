package tmg.flashback.data.repo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tmg.flashback.data.repo.mappers.app.ConstructorStandingMapper
import tmg.flashback.data.repo.mappers.app.DriverStandingMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorStandingMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverStandingMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.races.ConstructorStandings
import tmg.flashback.flashbackapi.api.models.races.DriverStandings
import tmg.flashback.formula1.model.SeasonConstructorStandings
import tmg.flashback.formula1.model.SeasonDriverStandings
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface StandingsRepository {
    suspend fun populateStandings(season: Int): Response
    fun getDriverStandings(season: Int): Flow<SeasonDriverStandings?>
    fun getConstructorStandings(season: Int): Flow<SeasonConstructorStandings?>
}

internal class StandingsRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkConstructorDataMapper: NetworkConstructorDataMapper,
    private val networkDriverDataMapper: NetworkDriverDataMapper,
    private val driverStandingMapper: DriverStandingMapper,
    private val constructorStandingMapper: ConstructorStandingMapper,
    private val networkDriverStandingMapper: NetworkDriverStandingMapper,
    private val networkConstructorStandingMapper: NetworkConstructorStandingMapper,
) : StandingsRepository {

    override suspend fun populateStandings(season: Int) = api.makeRequest(
        request = { api.getStandings(season) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            saveConstructors(data.constructors)
            saveDrivers(data.drivers)

            saveConstructorStandings(season, data.constructorStandings)
            saveDriverStandings(season, data.driverStandings)

            return@makeRequest true
        })

    override fun getDriverStandings(season: Int): Flow<SeasonDriverStandings?> {
        return persistence.seasonStandingDao()
            .getDriverStandings(season)
            .map { standings ->
                driverStandingMapper.mapDriverStanding(standings)
            }
    }

    override fun getConstructorStandings(season: Int): Flow<SeasonConstructorStandings?> {
        return persistence.seasonStandingDao()
            .getConstructorStandings(season)
            .map { standings ->
                constructorStandingMapper.mapConstructorStanding(standings)
            }
    }

    private suspend fun saveConstructorStandings(season: Int, constructors: Map<String, ConstructorStandings>?) {
        if (constructors == null) return
        val standings = constructors.values
            .map { networkConstructorStandingMapper.mapConstructorStanding(season, it) }
        val driverStandings = constructors.values
            .map { networkConstructorStandingMapper.mapConstructorStandingDriver(season, it) }
            .flatten()

        persistence.seasonStandingDao().insertConstructorStandings(standings, driverStandings)
    }

    private suspend fun saveDriverStandings(season: Int, drivers: Map<String, DriverStandings>?) {
        if (drivers == null) return
        val standings = drivers.values
            .map { networkDriverStandingMapper.mapDriverStanding(season, it) }
        val driverStandings = drivers.values
            .map { networkDriverStandingMapper.mapDriverStandingConstructor(season, it) }
            .flatten()

        persistence.seasonStandingDao().insertDriverStandings(standings, driverStandings)
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
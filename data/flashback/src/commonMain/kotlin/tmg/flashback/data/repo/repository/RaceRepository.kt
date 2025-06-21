package tmg.flashback.data.repo.repository

import tmg.flashback.data.repo.extensions.valueList
import tmg.flashback.data.repo.mappers.network.NetworkConstructorDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkConstructorStandingMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkDriverStandingMapper
import tmg.flashback.data.repo.mappers.network.NetworkRaceDataMapper
import tmg.flashback.data.repo.mappers.network.NetworkRaceMapper
import tmg.flashback.data.repo.mappers.network.NetworkScheduleMapper
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.utils.makeRequest
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.flashbackapi.api.models.constructors.Constructor
import tmg.flashback.flashbackapi.api.models.drivers.Driver
import tmg.flashback.flashbackapi.api.models.races.ConstructorStandings
import tmg.flashback.flashbackapi.api.models.races.DriverStandings
import tmg.flashback.persistence.flashback.FlashbackDatabase

interface RaceRepository {
    suspend fun populateRaces(season: Int): Response
    suspend fun populateRace(season: Int, round: Int): Response
}

internal class RaceRepositoryImpl(
    private val api: FlashbackApi,
    private val persistence: FlashbackDatabase,
    private val networkConstructorDataMapper: NetworkConstructorDataMapper,
    private val networkDriverDataMapper: NetworkDriverDataMapper,
    private val networkRaceDataMapper: NetworkRaceDataMapper,
    private val networkRaceMapper: NetworkRaceMapper,
    private val networkDriverStandingMapper: NetworkDriverStandingMapper,
    private val networkConstructorStandingMapper: NetworkConstructorStandingMapper,
    private val networkScheduleMapper: NetworkScheduleMapper,
): RaceRepository {
    override suspend fun populateRaces(season: Int) = api.makeRequest(
        request = { api.getSeason(season) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            saveConstructors(data.constructors)
            saveDrivers(data.drivers)

            saveConstructorStandings(season, data.constructorStandings)
            saveDriverStandings(season, data.driverStandings)

            val raceData = data.races.valueList().map { networkRaceDataMapper.mapRaceData(it.data) }
            val qualifyingResults = data.races.valueList()
                .map { race -> race.qualifying?.values?.map { Pair(race.data, it) } ?: emptyList() }
                .flatten()
                .map { (raceData, qualifying) -> networkRaceMapper.mapQualifyingResults(raceData.season, raceData.round, qualifying) }
            val raceResults = data.races.valueList()
                .map { race -> race.race?.values?.map { Pair(race.data, it) } ?: emptyList() }
                .flatten()
                .map { (raceData, race) -> networkRaceMapper.mapRaceResults(raceData.season, raceData.round, race) }

            val sprintQualifyingResults = data.races.valueList()
                .map { race -> race.sprintEvent?.qualifying?.values?.map { Pair(race.data, it) } ?: emptyList() }
                .flatten()
                .map { (raceData, sprint) -> networkRaceMapper.mapSprintQualifyingResult(raceData.season, raceData.round, sprint)}
            val sprintRaceResults = data.races.valueList()
                .map { race -> race.sprintEvent?.race?.values?.map { Pair(race.data, it) } ?: emptyList() }
                .flatten()
                .map { (raceData, sprint) -> networkRaceMapper.mapSprintRaceResults(raceData.season, raceData.round, sprint)}

            val schedules = data.races.valueList()
                .map { race -> networkScheduleMapper.mapSchedules(race) }
                .flatten()

            persistence.seasonDao().insertRaces(raceData, qualifyingResults, raceResults, sprintQualifyingResults, sprintRaceResults)
            persistence.scheduleDao().replaceAllForSeason(season, schedules)

            return@makeRequest true
        }
    )

    override suspend fun populateRace(season: Int, round: Int) = api.makeRequest(
        request = { api.getSeason(season, round) },
        response = { response ->
            val data = response?.data ?: return@makeRequest false
            saveConstructors(data.constructors)
            saveDrivers(data.drivers)

            val raceData = networkRaceDataMapper.mapRaceData(data.data)
            val qualifyingResults = data.qualifying
                .valueList()
                .map { networkRaceMapper.mapQualifyingResults(data.data.season, data.data.round, it) }
            val raceResults = data.race
                .valueList()
                .map { networkRaceMapper.mapRaceResults(data.data.season, data.data.round, it) }
            val sprintQualifyingResults = data.sprintEvent?.qualifying
                .valueList()
                .map { networkRaceMapper.mapSprintQualifyingResult(data.data.season, data.data.round, it) }
            val sprintRaceResults = data.sprintEvent?.race
                .valueList()
                .map { networkRaceMapper.mapSprintRaceResults(data.data.season, data.data.round, it) }
            val schedules = data.schedule
                ?.map { schedule -> networkScheduleMapper.mapSchedule(data.data.season, data.data.round, schedule) } ?: emptyList()

            persistence.scheduleDao().replaceAllForRace(data.data.season, data.data.round, schedules)
            persistence.seasonDao().insertRace(raceData, qualifyingResults, raceResults, sprintQualifyingResults, sprintRaceResults)

            return@makeRequest true
        }
    )

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
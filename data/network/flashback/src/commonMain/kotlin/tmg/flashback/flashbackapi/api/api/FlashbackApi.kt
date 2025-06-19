package tmg.flashback.flashbackapi.api.api

import io.ktor.client.HttpClient
import tmg.flashback.flashbackapi.api.models.MetadataWrapper
import tmg.flashback.flashbackapi.api.models.circuits.AllCircuits
import tmg.flashback.flashbackapi.api.models.circuits.CircuitHistory
import tmg.flashback.flashbackapi.api.models.constructors.AllConstructors
import tmg.flashback.flashbackapi.api.models.constructors.ConstructorHistory
import tmg.flashback.flashbackapi.api.models.drivers.AllDrivers
import tmg.flashback.flashbackapi.api.models.drivers.DriverHistory
import tmg.flashback.flashbackapi.api.models.overview.Event
import tmg.flashback.flashbackapi.api.models.overview.Overview
import tmg.flashback.flashbackapi.api.models.races.Round
import tmg.flashback.flashbackapi.api.models.races.Season

interface FlashbackApi {

    suspend fun getOverview(): MetadataWrapper<Overview>?

    suspend fun getOverview(season: Int): MetadataWrapper<Overview>?

    suspend fun getOverviewEvents(season: Int): MetadataWrapper<List<Event>>?

    suspend fun getDrivers(): MetadataWrapper<AllDrivers>?

    suspend fun getConstructors(): MetadataWrapper<AllConstructors>?

    suspend fun getCircuits(): MetadataWrapper<AllCircuits>?

    suspend fun getCircuit(id: String): MetadataWrapper<CircuitHistory>?

    suspend fun getDriver(id: String): MetadataWrapper<DriverHistory>?

    suspend fun getConstructor(id: String): MetadataWrapper<ConstructorHistory>?

    suspend fun getSeason(season: Int): MetadataWrapper<Season>?

    suspend fun getSeason(season: Int, round: Int): MetadataWrapper<Round>?

    suspend fun getSeasonEvents(season: Int): MetadataWrapper<List<Event>>?
}
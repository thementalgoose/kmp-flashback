package tmg.flashback.flashbackapi.api.api

import kotlinx.io.IOException
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

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getOverview(): MetadataWrapper<Overview>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getOverview(season: Int): MetadataWrapper<Overview>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getOverviewEvents(season: Int): MetadataWrapper<List<Event>>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getDrivers(): MetadataWrapper<AllDrivers>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getConstructors(): MetadataWrapper<AllConstructors>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getCircuits(): MetadataWrapper<AllCircuits>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getCircuit(id: String): MetadataWrapper<CircuitHistory>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getDriver(id: String): MetadataWrapper<DriverHistory>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getConstructor(id: String): MetadataWrapper<ConstructorHistory>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getSeason(season: Int): MetadataWrapper<Season>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getSeason(season: Int, round: Int): MetadataWrapper<Round>?

    @Throws(RuntimeException::class, IOException::class)
    suspend fun getSeasonEvents(season: Int): MetadataWrapper<List<Event>>?
}
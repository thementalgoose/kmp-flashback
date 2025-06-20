package tmg.flashback.flashbackapi.api.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import kotlinx.io.IOException
import tmg.flashback.flashbackapi.api.NetworkConfigurationManager
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
import tmg.flashback.infrastructure.debug.isDebug

class FlashbackApiImpl(
    private val httpClient: HttpClient,
    private val networkConfigurationManager: NetworkConfigurationManager
): FlashbackApi {

    override suspend fun getOverview(): MetadataWrapper<Overview>? =
        makeRequest("overview.json")

    override suspend fun getOverview(season: Int): MetadataWrapper<Overview>? =
        makeRequest("overview/$season.json")

    override suspend fun getOverviewEvents(season: Int): MetadataWrapper<List<Event>>? =
        makeRequest("overview/$season/events.json")

    override suspend fun getDrivers(): MetadataWrapper<AllDrivers>? =
        makeRequest("drivers.json")

    override suspend fun getConstructors(): MetadataWrapper<AllConstructors>? =
        makeRequest("constructors.json")

    override suspend fun getCircuits(): MetadataWrapper<AllCircuits>? =
        makeRequest("circuits.json")

    override suspend fun getCircuit(id: String): MetadataWrapper<CircuitHistory>? =
        makeRequest("circuits/$id.json")

    override suspend fun getDriver(id: String): MetadataWrapper<DriverHistory>? =
        makeRequest("drivers/$id.json")

    override suspend fun getConstructor(id: String): MetadataWrapper<ConstructorHistory>? =
        makeRequest("constructors/$id.json")

    override suspend fun getSeason(season: Int): MetadataWrapper<Season>? =
        makeRequest("races/$season.json")

    override suspend fun getSeason(
        season: Int,
        round: Int
    ): MetadataWrapper<Round>? =
        makeRequest("races/$season/$round.json")

    override suspend fun getSeasonEvents(season: Int): MetadataWrapper<List<Event>>? =
        makeRequest("races/$season/events.json")

    private val baseUrl: String
        get() = networkConfigurationManager.baseUrl

    private suspend inline fun <reified T> makeRequest(endpoint: String): T? {
        return try {
            httpClient.get("$baseUrl/$endpoint").body()
        } catch (e: IOException) {
            if (isDebug()) {
                e.printStackTrace()
            }
            null
        } catch (e: ClientRequestException) {
            if (isDebug()) {
                e.printStackTrace()
            }
            null
        } catch (e: RuntimeException) {
            if (isDebug()) {
                e.printStackTrace()
            }
            null
        } catch (e: Exception) {
            if (isDebug()) {
                e.printStackTrace()
            }
            null
        }
    }
}
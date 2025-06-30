package tmg.flashback.flashbackapi.api.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import tmg.flashback.flashbackapi.api.client.json
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
import tmg.flashback.flashbackapi.api.repositories.NetworkConfigRepository
import tmg.flashback.infrastructure.log.logInfo

class FlashbackApiImpl(
    private val httpClient: HttpClient,
    private val networkConfigRepository: NetworkConfigRepository
): FlashbackApi {

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getOverview(): MetadataWrapper<Overview>? =
        makeRequest("overview.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getOverview(season: Int): MetadataWrapper<Overview>? =
        makeRequest("overview/$season.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getOverviewEvents(season: Int): MetadataWrapper<List<Event>>? =
        makeRequest("overview/$season/events.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getDrivers(): MetadataWrapper<AllDrivers>? =
        makeRequest("drivers.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getConstructors(): MetadataWrapper<AllConstructors>? =
        makeRequest("constructors.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getCircuits(): MetadataWrapper<AllCircuits>? =
        makeRequest("circuits.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getCircuit(id: String): MetadataWrapper<CircuitHistory>? =
        makeRequest("circuits/$id.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getDriver(id: String): MetadataWrapper<DriverHistory>? =
        makeRequest("drivers/$id.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getConstructor(id: String): MetadataWrapper<ConstructorHistory>? =
        makeRequest("constructors/$id.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getSeason(season: Int): MetadataWrapper<Season>? =
        makeRequest("races/$season.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getSeason(season: Int, round: Int): MetadataWrapper<Round>? =
        makeRequest("races/$season/$round.json")

    @Throws(RuntimeException::class, IOException::class)
    override suspend fun getSeasonEvents(season: Int): MetadataWrapper<List<Event>>? =
        makeRequest("races/$season/events.json")

    private val baseUrl: String
        get() = networkConfigRepository.baseUrl

    @Throws(RuntimeException::class, IOException::class)
    private suspend inline fun <reified T> makeRequest(endpoint: String): T? {
        logInfo(">> $baseUrl/$endpoint")
        val httpResponse = httpClient.get("$baseUrl/$endpoint") {
            headers {
                set("Accept", "application/json")
                set("ContentType", "application/json")
                set("content-type", "application/json")
            }
        }
        when (httpResponse.status) {
            HttpStatusCode.NotFound -> {
                throw NotFoundException()
            }
            HttpStatusCode.InternalServerError -> {
                throw RuntimeException()
            }
        }
        // body decoding ktor strategy not working for some reason
        return json.decodeFromString<T>(httpResponse.bodyAsText())
    }
}

class NotFoundException(): RuntimeException()


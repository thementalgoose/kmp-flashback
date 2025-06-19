package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

expect val KtorClient: HttpClient

internal expect fun isDebug(): Boolean

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = isDebug()
}
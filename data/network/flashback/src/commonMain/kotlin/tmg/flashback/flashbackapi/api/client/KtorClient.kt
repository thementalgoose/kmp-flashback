package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import tmg.flashback.infrastructure.debug.isDebug

expect val KtorClient: HttpClient

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = isDebug()
}
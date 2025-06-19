package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

internal actual fun isDebug() = false

actual val KtorClient: HttpClient by lazy {
    HttpClient {
        install(ContentNegotiation) {
            json
        }

        if (isDebug()) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}
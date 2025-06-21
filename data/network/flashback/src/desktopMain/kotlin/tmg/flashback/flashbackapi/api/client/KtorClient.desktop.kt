package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import tmg.flashback.infrastructure.device.Device

actual val KtorClient: HttpClient by lazy {
    HttpClient {
        install(ContentNegotiation) {
            json
        }

        if (Device.isDebug) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}
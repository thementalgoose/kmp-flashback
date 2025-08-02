package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import tmg.flashback.infrastructure.device.Device

actual val KtorClient: HttpClient by lazy {
    HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json, contentType = ContentType.Any)
        }

        if (Device.isDebug) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}
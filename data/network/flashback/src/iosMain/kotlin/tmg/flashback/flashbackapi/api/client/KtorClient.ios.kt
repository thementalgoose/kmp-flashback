package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import tmg.flashback.infrastructure.device.Device

actual val KtorClient: HttpClient by lazy {
    HttpClient(Darwin) {
        // default validation to throw exceptions for non-2xx responses
        expectSuccess = true

        if (Device.isDebug) {
            install(Logging) {
                level = LogLevel.ALL
            }
        }

        engine {
            configureRequest {
                setAllowsCellularAccess(true)
                setAllowsExpensiveNetworkAccess(true)
            }
        }

        install(ContentNegotiation) {
            json(json, contentType = ContentType.Any)
        }

//        install(HttpRequestRetry) {
//            retryConfig()
//        }
    }
}
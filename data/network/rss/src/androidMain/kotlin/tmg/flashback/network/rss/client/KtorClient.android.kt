package tmg.flashback.network.rss.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.xml.xml
import okhttp3.logging.HttpLoggingInterceptor
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.network.DnsUtils

actual val KtorClient: HttpClient by lazy {
    HttpClient(OkHttp) {
        // default validation to throw exceptions for non-2xx responses
        expectSuccess = true

        engine {
            config {
                dns(DnsUtils.dns)
            }
            // add logging interceptor
            if (Device.isDebug) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(
                            HttpLoggingInterceptor.Level.BODY,
                        )
                    },
                )
            }
        }

        install(ContentNegotiation) {
            xml(format = xml)
        }
    }
}
package tmg.flashback.flashbackapi.api.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.network.DnsUtils

actual val KtorClient: HttpClient by lazy {

    val okhttpClient = OkHttpClient.Builder().build()
    val dns = DnsUtils.getDns(okhttpClient)

    HttpClient(OkHttp) {
        // default validation to throw exceptions for non-2xx responses
        expectSuccess = true

        engine {
            preconfigured = okhttpClient
                .newBuilder()
                .dns(dns)
                .build()

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
            json(json, contentType = ContentType.Any)
        }
    }
}
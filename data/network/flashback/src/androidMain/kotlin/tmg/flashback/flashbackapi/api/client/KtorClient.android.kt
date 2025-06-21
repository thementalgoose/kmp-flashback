package tmg.flashback.flashbackapi.api.client

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import okhttp3.logging.HttpLoggingInterceptor
import tmg.flashback.infrastructure.device.Device

actual val KtorClient: HttpClient by lazy {
    Log.d("SDK","KtorClient Android")

    HttpClient(OkHttp) {
        // default validation to throw exceptions for non-2xx responses
        expectSuccess = true

        engine {
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
            json
        }

//        install(HttpRequestRetry) {
//            retryConfig()
//        }
    }
}
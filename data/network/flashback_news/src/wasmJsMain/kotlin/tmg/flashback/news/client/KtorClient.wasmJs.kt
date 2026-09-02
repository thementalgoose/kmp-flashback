package tmg.flashback.news.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

actual val KtorClient: HttpClient by lazy {
    HttpClient(Js) {
        install(ContentNegotiation) {
            json(json, contentType = ContentType.Any)
        }
    }
}

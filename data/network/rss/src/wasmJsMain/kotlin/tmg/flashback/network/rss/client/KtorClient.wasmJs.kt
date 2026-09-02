package tmg.flashback.network.rss.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.xml.xml

actual val KtorClient: HttpClient by lazy {
    HttpClient(Js) {
        install(ContentNegotiation) {
            xml(xml)
        }
    }
}

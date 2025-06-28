package tmg.flashback.network.rss.client

import io.ktor.client.HttpClient
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML

expect val KtorClient: HttpClient

val xml = XML {
    xmlDeclMode = XmlDeclMode.Charset
    indent = 4
    autoPolymorphic = true
}
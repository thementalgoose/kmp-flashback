package tmg.flashback.network.rss.client

import io.ktor.client.HttpClient
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.DefaultXmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.XML

expect val KtorClient: HttpClient

@OptIn(ExperimentalXmlUtilApi::class)
val xml = XML {
    policy = DefaultXmlSerializationPolicy
        .Builder()
        .apply {
            this.ignoreNamespaces()
            this.ignoreUnknownChildren()
        }
        .build()
    xmlDeclMode = XmlDeclMode.Charset
    indent = 4
    autoPolymorphic = true
}
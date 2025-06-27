package tmg.flashback.network.rss.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.network.rss.api.RssApi
import tmg.flashback.network.rss.api.RssApiImpl
import tmg.flashback.network.rss.client.KtorClient

val dataNetworkRssModule = listOf(module())

internal fun module() = module {
    singleOf(::KtorClient)
    single<RssApi> { RssApiImpl(get()) }
}
package tmg.flashback.news.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.news.api.FlashbackNewsApi
import tmg.flashback.news.api.FlashbackNewsApiImpl
import tmg.flashback.news.client.KtorClient
import tmg.flashback.news.repositories.NewsRepository
import tmg.flashback.news.repositories.NewsRepositoryImpl

val dataNetworkFlashbackNewsModule = listOf(module())

internal fun module() = module {
    singleOf(::KtorClient)
    single<FlashbackNewsApi> { FlashbackNewsApiImpl(get(), get()) }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}
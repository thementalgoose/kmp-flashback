package tmg.flashback.flashbackapi.api.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.flashbackapi.api.api.FlashbackApi
import tmg.flashback.flashbackapi.api.api.FlashbackApiImpl
import tmg.flashback.flashbackapi.api.client.KtorClient
import tmg.flashback.flashbackapi.api.repositories.NetworkConfigRepository
import tmg.flashback.flashbackapi.api.repositories.NetworkConfigRepositoryImpl

val dataNetworkFlashbackModule = listOf(module())

internal fun module() = module {
    singleOf(::KtorClient)
    single<FlashbackApi> { FlashbackApiImpl(get(), get()) }

    // Temporary modules
    single<NetworkConfigRepository> { NetworkConfigRepositoryImpl(get()) }
}
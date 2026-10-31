package tmg.flashback.flashbackapi.api.di

import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import tmg.flashback.flashbackapi.api.client.KtorClient

@Module
@ComponentScan("tmg.flashback.flashbackapi.api")
@Configuration
class DataNetworkFlashbackModule {
    @Single
    @Named("flashback")
    fun provideHttpClient(): HttpClient = KtorClient
}

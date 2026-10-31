package tmg.flashback.network.rss.di

import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import tmg.flashback.network.rss.client.KtorClient

@Module
@ComponentScan("tmg.flashback.network.rss")
@Configuration
class DataNetworkRssModule {
    @Single
    @Named("rss")
    fun provideHttpClient(): HttpClient = KtorClient
}

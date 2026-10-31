package tmg.flashback.news.di

import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import tmg.flashback.news.client.KtorClient

@Module
@ComponentScan("tmg.flashback.news")
@Configuration
class DataNetworkFlashbackNewsModule {
    @Single
    @Named("flashback_news")
    fun provideHttpClient(): HttpClient = KtorClient
}

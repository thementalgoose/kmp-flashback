package tmg.flashback.feature.rss.di

import org.koin.dsl.module
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.feature.rss.repositories.RssRepositoryImpl
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCaseImpl

val featureRssModule = listOf(module())

internal fun module() = module {
    single<RssRepository> { RssRepositoryImpl(get()) }

    single<IsRssEnabledUseCase> { IsRssEnabledUseCaseImpl(get()) }
}
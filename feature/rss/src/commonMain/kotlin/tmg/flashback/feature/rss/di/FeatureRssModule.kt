package tmg.flashback.feature.rss.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.rss.mapper.RssXMLMapper
import tmg.flashback.feature.rss.mapper.RssXMLMapperImpl
import tmg.flashback.feature.rss.presentation.configure.RssConfigureViewModel
import tmg.flashback.feature.rss.presentation.feed.RSSFeedViewModel
import tmg.flashback.feature.rss.repositories.RssRepository
import tmg.flashback.feature.rss.repositories.RssRepositoryImpl
import tmg.flashback.feature.rss.usecases.GetRssArticleUseCase
import tmg.flashback.feature.rss.usecases.GetRssArticlesUseCaseImpl
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCaseImpl

val featureRssModule = listOf(module())

internal fun module() = module {
    viewModel { RssConfigureViewModel(get()) }
    viewModel { RSSFeedViewModel(get(), get(), get(), get(), get(), get()) }

    single<RssRepository> { RssRepositoryImpl(get(), get()) }

    single<RssXMLMapper> { RssXMLMapperImpl() }

    single<GetRssArticleUseCase> { GetRssArticlesUseCaseImpl(get(), get(), get()) }
    single<IsRssEnabledUseCase> { IsRssEnabledUseCaseImpl(get()) }
}
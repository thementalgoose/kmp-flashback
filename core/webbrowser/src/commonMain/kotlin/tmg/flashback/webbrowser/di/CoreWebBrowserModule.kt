package tmg.flashback.webbrowser.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.webbrowser.presentation.WebViewViewModel
import tmg.flashback.webbrowser.repository.WebRepository
import tmg.flashback.webbrowser.repository.WebRepositoryImpl
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCase
import tmg.flashback.webbrowser.usecases.IsInAppBrowserEnabledUseCaseImpl

val coreWebBrowserModule = listOf(modules(), platformModule())

internal fun modules() = module {
    single<WebRepository> { WebRepositoryImpl(get()) }
    viewModel { WebViewViewModel(get(), get(), get()) }
    single<IsInAppBrowserEnabledUseCase> { IsInAppBrowserEnabledUseCaseImpl() }
}
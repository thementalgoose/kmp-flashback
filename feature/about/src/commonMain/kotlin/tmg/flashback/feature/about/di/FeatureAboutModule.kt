package tmg.flashback.feature.about.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.about.presentation.AboutViewModel

val featureAboutModule = listOf(module())

internal fun module() = module {
    viewModel { AboutViewModel(get()) }
}
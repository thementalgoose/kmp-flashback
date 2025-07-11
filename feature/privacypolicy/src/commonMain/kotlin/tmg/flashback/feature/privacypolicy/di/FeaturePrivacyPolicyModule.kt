package tmg.flashback.feature.privacypolicy.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyViewModel

val featurePrivacyPolicyModule = listOf(module())

internal fun module() = module {
    viewModel { PrivacyPolicyViewModel(get()) }
}
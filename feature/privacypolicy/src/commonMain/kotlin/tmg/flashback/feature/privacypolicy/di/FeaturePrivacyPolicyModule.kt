package tmg.flashback.feature.privacypolicy.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.privacypolicy.presentation.PrivacyPolicyViewModel
import tmg.flashback.feature.privacypolicy.repository.PrivacyRepository
import tmg.flashback.feature.privacypolicy.repository.PrivacyRepositoryImpl

val featurePrivacyPolicyModule = listOf(module())

internal fun module() = module {
    viewModel { PrivacyPolicyViewModel(get(), get()) }

    single<PrivacyRepository> { PrivacyRepositoryImpl(get()) }
}
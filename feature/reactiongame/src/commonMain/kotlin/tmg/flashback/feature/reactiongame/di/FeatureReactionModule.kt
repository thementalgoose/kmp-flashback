package tmg.flashback.feature.reactiongame.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.reactiongame.manager.LightsOutDelayProvider
import tmg.flashback.feature.reactiongame.manager.LightsOutDelayProviderImpl
import tmg.flashback.feature.reactiongame.presentation.ReactionGameViewModel

val featureReactionGameModule = listOf(module())

internal fun module() = module {
    viewModel { ReactionGameViewModel(get(), get(), Dispatchers.IO) }

    single<LightsOutDelayProvider> { LightsOutDelayProviderImpl() }
}
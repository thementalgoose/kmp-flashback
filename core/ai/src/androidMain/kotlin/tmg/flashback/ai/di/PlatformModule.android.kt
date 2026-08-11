package tmg.flashback.ai.di

import org.koin.dsl.module
import tmg.flashback.ai.manager.AiManager
import tmg.flashback.ai.manager.AiManagerImpl

internal actual fun platformModule() = module {
    single<AiManager> { AiManagerImpl(get()) }
}
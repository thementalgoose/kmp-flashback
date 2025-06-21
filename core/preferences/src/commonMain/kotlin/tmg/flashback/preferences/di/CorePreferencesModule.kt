package tmg.flashback.preferences.di

import org.koin.dsl.module
import tmg.flashback.preferences.manager.PreferenceManager
import tmg.flashback.preferences.manager.PreferenceManagerImpl

val corePreferencesModule = listOf(platformModule(), module())

internal fun module() = module {
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
}
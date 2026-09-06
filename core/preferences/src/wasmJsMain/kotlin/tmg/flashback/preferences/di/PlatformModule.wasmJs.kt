package tmg.flashback.preferences.di

import org.koin.dsl.module
import tmg.flashback.preferences.service.StorageService

internal actual fun platformModule() = module {
    single { StorageService() }
}

package tmg.flashback.preferences.di

import org.koin.dsl.module
import tmg.flashback.preferences.service.StorageService

actual fun platformModule() = module {
    single { StorageService() }
}
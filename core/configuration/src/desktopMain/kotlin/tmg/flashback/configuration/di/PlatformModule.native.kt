package tmg.flashback.configuration.di

import org.koin.dsl.module
import tmg.flashback.configuration.firebase.FirebaseService

actual fun platformModule() = module {
    single { FirebaseService() }
}
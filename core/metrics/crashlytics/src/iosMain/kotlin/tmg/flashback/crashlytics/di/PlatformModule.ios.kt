package tmg.flashback.crashlytics.di

import org.koin.dsl.module
import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService

actual fun platformModule() = module {
    single { FirebaseCrashlyticsService() }
}

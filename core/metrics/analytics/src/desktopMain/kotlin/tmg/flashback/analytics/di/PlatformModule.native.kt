package tmg.flashback.analytics.di

import org.koin.core.module.Module
import org.koin.dsl.module
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService

actual fun platformModule() = module {
    single { FirebaseAnalyticsService() }
}
package tmg.flashback.analytics.di

import org.koin.dsl.module
import platform.AppTrackingTransparency.ATTrackingManager

actual fun platformModule() = module {
}

fun test() {
    ATTrackingManager.initialize()
    ATTrackingManager.requestTrackingAuthorizationWithCompletionHandler {

    }
}
package tmg.flashback.ui.di

import org.koin.dsl.module
import tmg.flashback.ui.activity.ActivityProvider

actual fun platformModule() = module {
    single { ActivityProvider() }
}
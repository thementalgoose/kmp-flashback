package tmg.flashback.notifications.di

import org.koin.dsl.module
import tmg.flashback.notifications.manager.SystemAlarmManager

internal actual fun platformModule() = module {
    single { SystemAlarmManager() }
}
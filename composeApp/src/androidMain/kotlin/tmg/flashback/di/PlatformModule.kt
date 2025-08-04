package tmg.flashback.di

import org.koin.dsl.module
import tmg.flashback.FlashbackAndroidStartup
import tmg.flashback.navigation.WidgetNavigatorImpl
import tmg.flashback.widgets.upnext.navigation.WidgetNavigator

actual fun platformModule() = module {
    single { FlashbackAndroidStartup(get()) }
    single<WidgetNavigator> { WidgetNavigatorImpl() }
}
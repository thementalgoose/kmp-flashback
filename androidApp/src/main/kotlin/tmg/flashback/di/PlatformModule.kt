package tmg.flashback.di

import org.koin.dsl.module
import tmg.flashback.navigation.WidgetNavigatorImpl
import tmg.flashback.widgets.upnext.navigation.WidgetNavigator

fun androidAppModule() = module {
    single<WidgetNavigator> { WidgetNavigatorImpl() }
}
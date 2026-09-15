package tmg.flashback.widgets.upnext.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.preferences.di.CorePreferencesModule

@Module(includes = [CorePreferencesModule::class])
@ComponentScan("tmg.flashback.widgets.upnext")
class FeatureWidgetUpNextModule

val widgetUpNextPlatformModule = platformModule()
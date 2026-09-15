package tmg.flashback.widgets.upnext.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.widgets.upnext")
class FeatureWidgetUpNextModule

val widgetUpNextPlatformModule = platformModule()
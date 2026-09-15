package tmg.flashback.webbrowser.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.device.di.CoreDeviceModule
import tmg.flashback.preferences.di.CorePreferencesModule

@Module(includes = [CoreDeviceModule::class, CorePreferencesModule::class])
@ComponentScan("tmg.flashback.webbrowser")
class CoreWebBrowserModule

val webBrowserPlatformModule = platformModule()
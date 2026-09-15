package tmg.flashback.webbrowser.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.webbrowser")
class CoreWebBrowserModule

val webBrowserPlatformModule = platformModule()
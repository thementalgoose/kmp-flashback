package tmg.flashback.ui.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.ui")
class PresentationUiModule

val presentationUiPlatformModule = platformModule()
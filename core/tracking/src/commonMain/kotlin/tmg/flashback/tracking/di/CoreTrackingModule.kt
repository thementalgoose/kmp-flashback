package tmg.flashback.tracking.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.tracking")
class CoreTrackingModule

val trackingPlatformModule = platformModule()
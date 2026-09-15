package tmg.flashback.persistence.flashback.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("tmg.flashback.persistence.flashback")
class FlashbackDBModule

val persistencePlatformModule = platformModule()
package tmg.flashback.data.repo.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.flashbackapi.api.di.FlashbackApiModule
import tmg.flashback.persistence.flashback.di.FlashbackDBModule

@Module(includes = [FlashbackApiModule::class, FlashbackDBModule::class])
@ComponentScan("tmg.flashback.data.repo")
class DataFlashbackModule
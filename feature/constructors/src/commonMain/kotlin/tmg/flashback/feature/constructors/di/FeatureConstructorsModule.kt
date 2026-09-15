package tmg.flashback.feature.constructors.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.data.repo.di.DataFlashbackModule

@Module(includes = [DataFlashbackModule::class])
@ComponentScan("tmg.flashback.feature.constructors")
class FeatureConstructorsModule
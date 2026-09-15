package tmg.flashback.feature.circuits.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.data.repo.di.DataFlashbackModule
import tmg.flashback.device.di.CoreDeviceModule

@Module(includes = [DataFlashbackModule::class, CoreDeviceModule::class])
@ComponentScan("tmg.flashback.feature.circuits")
class FeatureCircuitsModule
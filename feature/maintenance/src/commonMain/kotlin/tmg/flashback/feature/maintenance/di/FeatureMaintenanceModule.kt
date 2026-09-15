package tmg.flashback.feature.maintenance.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import tmg.flashback.configuration.di.ConfigurationModule

@Module(includes = [ConfigurationModule::class])
@ComponentScan("tmg.flashback.feature.maintenance")
class FeatureMaintenanceModule
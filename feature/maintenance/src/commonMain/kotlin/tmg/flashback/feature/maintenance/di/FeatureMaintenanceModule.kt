package tmg.flashback.feature.maintenance.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeViewModel
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository
import tmg.flashback.feature.maintenance.repository.MaintenanceRepositoryImpl

val featureMaintenanceModule = listOf(module())

internal fun module() = module {
    viewModel { ForceUpgradeViewModel(get(), get(), get()) }

    single<MaintenanceRepository> { MaintenanceRepositoryImpl(get()) }
}
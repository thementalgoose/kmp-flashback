package tmg.flashback.feature.maintenance.di

import org.koin.dsl.module
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository
import tmg.flashback.feature.maintenance.repository.MaintenanceRepositoryImpl

val featureMaintenanceModule = listOf(module())

internal fun module() = module {
    single<MaintenanceRepository> { MaintenanceRepositoryImpl(get()) }
}
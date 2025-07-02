package tmg.flashback.device.di

import org.koin.dsl.module
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.repositories.DeviceRepositoryImpl

val coreDeviceModules = listOf(module())

internal fun module() = module {
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get()) }
}
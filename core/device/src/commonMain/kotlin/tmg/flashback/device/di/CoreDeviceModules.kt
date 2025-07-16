package tmg.flashback.device.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.repositories.DeviceRepositoryImpl
import tmg.flashback.device.usecases.OpenEmailUseCase
import tmg.flashback.device.usecases.OpenEmailUseCaseImpl
import tmg.flashback.device.usecases.OpenLocationUseCase
import tmg.flashback.device.usecases.OpenLocationUseCaseImpl
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.device.usecases.OpenSettingsUseCaseImpl
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.device.usecases.OpenStorePageUseCaseImpl
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCaseImpl

val coreDeviceModules = listOf(module())

internal fun module() = module {
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get()) }

    singleOf<OpenSettingsUseCase>(::OpenSettingsUseCaseImpl)
    singleOf<OpenWebpageUseCase>(::OpenWebpageUseCaseImpl)
    singleOf<OpenEmailUseCase>(::OpenEmailUseCaseImpl)
    singleOf<OpenLocationUseCase>(::OpenLocationUseCaseImpl)
    singleOf<OpenStorePageUseCase>(::OpenStorePageUseCaseImpl)
}
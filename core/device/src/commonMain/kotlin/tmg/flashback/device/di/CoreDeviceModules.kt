package tmg.flashback.device.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.repositories.DeviceRepositoryImpl
import tmg.flashback.device.usecases.IsPermissionGrantedUseCase
import tmg.flashback.device.usecases.IsPermissionGrantedUseCaseImpl
import tmg.flashback.device.usecases.OpenSettingsUseCase
import tmg.flashback.device.usecases.OpenSettingsUseCaseImpl
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCaseImpl
import tmg.flashback.device.usecases.RequestPermissionUseCase
import tmg.flashback.device.usecases.RequestPermissionUseCaseImpl

val coreDeviceModules = listOf(module())

internal fun module() = module {
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get()) }

    singleOf<OpenSettingsUseCase>(::OpenSettingsUseCaseImpl)
    singleOf<OpenWebpageUseCase>(::OpenWebpageUseCaseImpl)
    singleOf<IsPermissionGrantedUseCase>(::IsPermissionGrantedUseCaseImpl)
    singleOf<RequestPermissionUseCase>(::RequestPermissionUseCaseImpl)
}
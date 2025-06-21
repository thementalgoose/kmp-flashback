package tmg.flashback.crashlytics.di

import org.koin.dsl.module
import tmg.flashback.crashlytics.manager.CrashlyticsManager
import tmg.flashback.crashlytics.manager.CrashlyticsManagerImpl
import tmg.flashback.crashlytics.usecases.AddCustomKeyUseCase
import tmg.flashback.crashlytics.usecases.AddCustomKeyUseCaseImpl
import tmg.flashback.crashlytics.usecases.InitialiseCrashlyticsUseCase
import tmg.flashback.crashlytics.usecases.InitialiseCrashlyticsUseCaseImpl

val coreMetricsCrashlyticsModule = listOf(platformModule(), module())

internal fun module() = module {
    single<CrashlyticsManager> { CrashlyticsManagerImpl(get()) }
    single<InitialiseCrashlyticsUseCase> { InitialiseCrashlyticsUseCaseImpl(get()) }
    single<AddCustomKeyUseCase> { AddCustomKeyUseCaseImpl(get()) }
}
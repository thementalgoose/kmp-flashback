package tmg.flashback.analytics.di

import org.koin.dsl.module
import tmg.flashback.analytics.manager.AnalyticsManager
import tmg.flashback.analytics.manager.AnalyticsManagerImpl
import tmg.flashback.analytics.usecases.InitialiseAnalyticsUseCase
import tmg.flashback.analytics.usecases.InitialiseAnalyticsUseCaseImpl
import tmg.flashback.analytics.usecases.SetUserPropertyUseCase
import tmg.flashback.analytics.usecases.SetUserPropertyUseCaseImpl

val coreMetricsAnalyticsModule = listOf(platformModule(), module())

internal fun module() = module {
    single<InitialiseAnalyticsUseCase> { InitialiseAnalyticsUseCaseImpl(get()) }
    single<AnalyticsManager> { AnalyticsManagerImpl(get()) }
    single<SetUserPropertyUseCase> { SetUserPropertyUseCaseImpl(get()) }
}
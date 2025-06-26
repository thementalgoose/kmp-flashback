package tmg.flashback.feature.weekend.di

import org.koin.dsl.module
import tmg.flashback.feature.weekend.repositories.WeatherRepository
import tmg.flashback.feature.weekend.repositories.WeatherRepositoryImpl

val featureWeekendModule = listOf(module())

internal fun module() = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}
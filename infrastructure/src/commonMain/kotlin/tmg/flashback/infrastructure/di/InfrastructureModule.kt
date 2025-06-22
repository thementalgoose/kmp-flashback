package tmg.flashback.infrastructure.di

import org.koin.dsl.module
import tmg.flashback.infrastructure.datetime.TimeManager
import tmg.flashback.infrastructure.datetime.TimeManagerImpl

val infrastructureModule = listOf(module())

internal fun module() = module {
    single<TimeManager> { TimeManagerImpl() }
}
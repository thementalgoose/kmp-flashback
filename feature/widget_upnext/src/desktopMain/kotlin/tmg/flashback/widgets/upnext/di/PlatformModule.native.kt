package tmg.flashback.widgets.upnext.di

import org.koin.dsl.module
import tmg.flashback.widgets.upnext.usecases.HasUpNextWidgetsUseCase
import tmg.flashback.widgets.upnext.usecases.HasUpNextWidgetsUseCaseImpl

actual fun platformModule() = module {
    single<HasUpNextWidgetsUseCase> { HasUpNextWidgetsUseCaseImpl() }
}
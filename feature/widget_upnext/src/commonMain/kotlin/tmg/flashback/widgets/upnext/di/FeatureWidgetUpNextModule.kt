package tmg.flashback.widgets.upnext.di

import org.koin.dsl.module
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepository
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepositoryImpl
import tmg.flashback.widgets.upnext.usecases.IsWidgetsEnabledUseCase
import tmg.flashback.widgets.upnext.usecases.IsWidgetsEnabledUseCaseImpl
import tmg.flashback.widgets.upnext.usecases.RefreshWidgetsUseCase
import tmg.flashback.widgets.upnext.usecases.RefreshWidgetsUseCaseImpl

val featureWidgetUpNextModule = listOf(module(), platformModule())

internal fun module() = module {
    single<IsWidgetsEnabledUseCase> { IsWidgetsEnabledUseCaseImpl() }
    single<UpNextWidgetRepository> { UpNextWidgetRepositoryImpl(get()) }
    single<RefreshWidgetsUseCase> { RefreshWidgetsUseCaseImpl() }
}
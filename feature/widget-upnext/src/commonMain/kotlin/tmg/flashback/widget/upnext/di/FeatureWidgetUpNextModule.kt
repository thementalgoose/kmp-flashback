package tmg.flashback.widget.upnext.di

import org.koin.dsl.module
import tmg.flashback.widget.upnext.usecases.IsWidgetsEnabledUseCase
import tmg.flashback.widget.upnext.usecases.IsWidgetsEnabledUseCaseImpl

val featureWidgetUpNextModule = listOf(module())

internal fun module() = module {
    single<IsWidgetsEnabledUseCase> { IsWidgetsEnabledUseCaseImpl() }
}
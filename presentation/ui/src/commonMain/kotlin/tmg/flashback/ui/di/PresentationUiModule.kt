package tmg.flashback.ui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.ui.permissions.PermissionManager

val presentationUiModule = listOf(module())

internal fun module() = module {
    singleOf(::PermissionManager)
}
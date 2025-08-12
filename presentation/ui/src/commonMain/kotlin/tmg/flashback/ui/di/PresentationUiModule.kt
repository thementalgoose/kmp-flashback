package tmg.flashback.ui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionManagerImpl
import tmg.flashback.ui.toasts.ToastManager
import tmg.flashback.ui.toasts.ToastManagerImpl

val presentationUiModule = listOf(module(), platformModule())

internal fun module() = module {
    singleOf<PermissionManager>(::PermissionManagerImpl)
    single<ToastManager> { ToastManagerImpl() }
}
package tmg.flashback.composeApp.manager

import org.jetbrains.compose.resources.StringResource
import org.koin.core.annotation.Single
import tmg.flashback.device.manager.UiManager
import tmg.flashback.ui.toasts.ToastManager

@Single
class UiManagerImpl(
    private val toastManager: ToastManager
): UiManager {
    override fun showToUser(resource: StringResource) {
        toastManager.showMessage(resource)
    }

    override fun showToUser(message: String) {
        toastManager.showMessage(message)
    }
}
package tmg.flashback.manager

import org.jetbrains.compose.resources.StringResource
import tmg.flashback.device.manager.UiManager
import tmg.flashback.ui.toasts.ToastManager

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
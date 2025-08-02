package tmg.flashback.ui.toast

actual class ToastManagerImpl actual constructor(): ToastManager {
    actual override fun showMessage(message: String, duration: Duration) {
        // no-op
    }
}
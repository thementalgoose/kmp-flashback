package tmg.flashback.ui.toast

interface ToastManager {
    fun showMessage(message: String, duration: Duration)
}

expect class ToastManagerImpl(): ToastManager {
    override fun showMessage(message: String, duration: Duration)
}

enum class Duration {
    Short,
    Long,
}
package tmg.flashback.ui.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.getKoin
import org.koin.compose.koinInject

interface ToastManager {
    fun showMessage(message: String, duration: Duration)
}

expect class ToastManagerImpl(): ToastManager {
    override fun showMessage(message: String, duration: Duration)
}

@Composable
fun rememberToastManager(
    toastManager: ToastManager = koinInject()
): ToastManager {
    return remember { toastManager }
}

enum class Duration {
    Short,
    Long,
}
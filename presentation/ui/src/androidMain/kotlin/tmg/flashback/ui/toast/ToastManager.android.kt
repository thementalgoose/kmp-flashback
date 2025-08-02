package tmg.flashback.ui.toast

import android.content.Context
import android.widget.Toast
import org.koin.core.component.KoinComponent

actual class ToastManagerImpl actual constructor(): ToastManager, KoinComponent {

    val applicationContext: Context
        get() = getKoin().get<Context>()

    actual override fun showMessage(message: String, duration: Duration) {
        val toastDuration = when (duration) {
            Duration.Short -> Toast.LENGTH_SHORT
            Duration.Long -> Toast.LENGTH_LONG
        }
        Toast
            .makeText(applicationContext, message, toastDuration)
            .show()
    }
}
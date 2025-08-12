package tmg.flashback.ui.toasts

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.runBlocking
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.ToastGravity
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class ToastManagerImpl: ToastManager {

    override var backgroundColor: Color = Color.Black
    override var foregroundColor: Color = Color.White


    override fun showMessage(
        resourceId: StringResource,
        duration: Duration
    ) {
        val message = runBlocking { getString(resourceId) }
        showMessage(
            message = message,
            duration = duration
        )
    }

    override fun showMessage(
        message: String,
        duration: Duration
    ) {
        showToast(
            message = message,
            gravity = ToastGravity.Bottom,
            backgroundColor = this.backgroundColor,
            textColor = this.foregroundColor,
            duration = when (duration) {
                Duration.Short -> ToastDuration.Short
                Duration.Long -> ToastDuration.Long
            },
        )
    }
}
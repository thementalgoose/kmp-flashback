package tmg.flashback.ui.toasts

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource

interface ToastManager {

    var backgroundColor: Color
    var foregroundColor: Color

    fun showMessage(
        message: String,
        duration: Duration = Duration.Long
    )
    fun showMessage(
        resourceId: StringResource,
        duration: Duration = Duration.Long
    )
}
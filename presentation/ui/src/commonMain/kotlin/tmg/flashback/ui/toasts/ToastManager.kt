package tmg.flashback.ui.toasts

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.ToastGravity
import multiplatform.network.cmptoast.showToast
import tmg.flashback.style.AppColors
import tmg.flashback.style.AppTheme

object ToastManager {
    fun showMessage(
        message: String,
        backgroundColor: Color = Color.Black,
        textColor: Color = Color.White
    ) {
        showToast(
            message = message,
            gravity = ToastGravity.Bottom,
            backgroundColor = backgroundColor,
            textColor = textColor,
            duration = ToastDuration.Long
        )
    }
}
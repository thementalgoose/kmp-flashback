package tmg.flashback.xr.components

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable

@Composable
actual fun XRDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = content
    )
}
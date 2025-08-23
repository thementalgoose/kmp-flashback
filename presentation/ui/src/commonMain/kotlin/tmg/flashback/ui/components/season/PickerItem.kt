package tmg.flashback.ui.components.season

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface PickerItem {
    data class Text(val text: String): PickerItem
    data class Label(val stringRes: StringResource): PickerItem
}

@Composable
fun PickerItem.string(): String {
    return when (this) {
        is PickerItem.Label -> stringResource(this.stringRes)
        is PickerItem.Text -> this.text
    }
}
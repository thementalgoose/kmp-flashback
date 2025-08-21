package tmg.flashback.ui.components.season

import org.jetbrains.compose.resources.StringResource

sealed interface PickerItem {
    data class Season(val season: Int): PickerItem
    data class Label(val stringRes: StringResource): PickerItem
}
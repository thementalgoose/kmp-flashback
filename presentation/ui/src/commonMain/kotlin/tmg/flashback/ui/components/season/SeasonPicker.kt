package tmg.flashback.ui.components.season

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextHeadline1Inline
import tmg.flashback.style.text.TextTitle

@Composable
fun Picker(
    option: PickerItem,
    optionsToShow: List<PickerItem>,
    optionUpdated: (PickerItem) -> Unit,
    modifier: Modifier = Modifier,
    labelContent: @Composable () -> Unit = { },
    defaultExpanded: Boolean = false
) {
    val expanded = remember { mutableStateOf(defaultExpanded)  }
    Row(
        modifier = modifier
            .clickable { expanded.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextHeadline1Inline(text = option.string())
        Spacer(Modifier.width(AppTheme.dimens.nsmall))
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            tint = AppTheme.colors.tertiary
        )
        Spacer(Modifier.width(AppTheme.dimens.small))
        labelContent()
        DropdownMenu(
            offset = DpOffset(AppTheme.dimens.medium, 0.dp),
            modifier = Modifier.background(AppTheme.colors.tertiaryContainer),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            content = {
                optionsToShow.forEach { season ->
                    DropdownMenuItem(
                        text = {
                            val string = when (val item = season) {
                                is PickerItem.Label -> stringResource(item.stringRes)
                                is PickerItem.Text -> item.text
                            }
                            TextTitle(
                                textColor = AppTheme.colors.onTertiaryContainer,
                                text = string,
                                bold = true
                            )
                        },
                        onClick = {
                            optionUpdated(season)
                            expanded.value = false
                        }
                    )
                }
            }
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Picker(
            option = PickerItem.Text("2020"),
            optionsToShow = listOf(PickerItem.Text("2020"), PickerItem.Text("2021")),
            optionUpdated = { },
        )
    }
}


@PreviewTheme
@Composable
private fun PreviewExpanded() {
    ApplicationThemePreview {
        Picker(
            option = PickerItem.Text("2020"),
            optionsToShow = listOf(PickerItem.Text("2020"), PickerItem.Text("2021")),
            optionUpdated = { },
            defaultExpanded = true
        )
    }
}


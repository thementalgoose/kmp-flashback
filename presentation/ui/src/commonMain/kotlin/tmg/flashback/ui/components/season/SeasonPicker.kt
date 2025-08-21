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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextHeadline1Inline
import tmg.flashback.style.text.TextTitle

@Composable
fun SeasonPicker(
    season: Int,
    seasonsToShow: List<PickerItem>,
    seasonUpdated: (PickerItem) -> Unit,
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
        TextHeadline1Inline(text = season.toString())
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
                seasonsToShow.forEach { season ->
                    DropdownMenuItem(
                        text = {
                            val string = when (val item = season) {
                                is PickerItem.Label -> stringResource(item.stringRes)
                                is PickerItem.Season -> item.season.toString()
                            }
                            TextTitle(
                                textColor = AppTheme.colors.onTertiaryContainer,
                                text = string,
                                bold = true
                            )
                        },
                        onClick = {
                            seasonUpdated(season)
                            expanded.value = false
                        }
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        SeasonPicker(
            season = 2020,
            seasonsToShow = listOf(PickerItem.Season(2020), PickerItem.Season(2021)),
            seasonUpdated = { },
        )
    }
}


@Preview
@Composable
private fun PreviewExpanded(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        SeasonPicker(
            season = 2020,
            seasonsToShow = listOf(PickerItem.Season(2020), PickerItem.Season(2021)),
            seasonUpdated = { },
            defaultExpanded = true
        )
    }
}


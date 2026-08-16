package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_race
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.nav_qualifying
import org.jetbrains.compose.resources.painterResource
import tmg.flashback.feature.weekend.presentation.data.QualifyingSortType
import tmg.flashback.feature.weekend.presentation.data.ResultType
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.QualifyingType
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.season.Picker
import tmg.flashback.ui.components.season.PickerItem

@Composable
internal fun TypeHeader(
    resource: StringResource,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextHeadline2(
            text = stringResource(resource),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
internal fun <E: Enum<E>> TypeHeader(
    resource: StringResource,
    option: E,
    options: List<E>,
    optionClicked: (E) -> Unit,
    modifier: Modifier = Modifier,
    optionsToShowBadge: List<E> = options,
    optionLabel: @Composable (E) -> String = @Composable { it.name },
) {
    val expanded = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppTheme.dimens.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextHeadline2(
            text = stringResource(resource),
            modifier = Modifier.weight(1f)
        )
        if (optionsToShowBadge.contains(option)) {
            BadgeView(
                label = optionLabel(option),
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    onClick = { expanded.value = true }
                )
            )
        }
        Column {
            IconButton(
                onClick = {
                    expanded.value = true
                },
                interactionSource = interactionSource,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Sort,
                        contentDescription = null,
                        tint = AppTheme.colors.onSurface,
                    )
                }
            )
            SortList(
                optionList = options,
                expanded = expanded,
                optionClicked = optionClicked,
                optionLabel = optionLabel
            )
        }
    }
}

@Composable
private fun <E : Enum<E>> SortList(
    optionList: List<E>,
    expanded: MutableState<Boolean>,
    optionClicked: (E) -> Unit,
    optionLabel: @Composable (E) -> String = @Composable { it.name },
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        modifier = modifier.background(AppTheme.colors.tertiaryContainer),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        content = {
            optionList.forEach { item ->
                DropdownMenuItem(
                    text = {
                        TextTitle(
                            textColor = AppTheme.colors.onTertiaryContainer,
                            text = optionLabel(item),
                            bold = true
                        )
                    },
                    onClick = {
                        optionClicked(item)
                        expanded.value = false
                    }
                )
            }
        }
    )
}

@PreviewTheme
@Composable
private fun PreviewTypeHeaderRegular() {
    ApplicationThemePreview {
        TypeHeader(
            resource = string.nav_race
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewTypeHeaderResult() {
    ApplicationThemePreview {
        TypeHeader(
            resource = string.nav_race,
            options = QualifyingSortType.entries,
            optionClicked = { },
            option = QualifyingSortType.Qualified
        )
    }
}
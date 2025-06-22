package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.empty
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextBody1

val columnWidthCollapsed: Dp = 64.dp
private val itemSize: Dp = 48.dp
private val iconSize: Dp = 24.dp
val columnWidthExpanded: Dp = 240.dp

@Composable
fun NavigationColumn(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    lockExpanded: Boolean = false,
    contentHeader: @Composable ColumnScope.() -> Unit = {}
) {
    val expanded = remember { mutableStateOf(lockExpanded) }
    val width = animateDpAsState(targetValue = when (expanded.value) {
        true -> columnWidthExpanded
        false -> columnWidthCollapsed
    }, label = "width")

    Column(modifier = modifier
        .width(width.value)
        .fillMaxHeight()
        .background(AppTheme.colors.surfaceNav)
        .padding(
            vertical = AppTheme.dimens.small
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            content = {
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(AppTheme.dimens.small))
                    }
                }
                items(list) {
                    NavigationItem(
                        item = it,
                        isExpanded = expanded.value,
                        onClick = itemClicked,
                    )
                    Spacer(Modifier.height(AppTheme.dimens.small))
                }
                item {
                    Spacer(modifier = Modifier.height(AppTheme.dimens.small))
                }
            }
        )
        if (!lockExpanded) {
            NavigationItem(
                item = NavigationItem(
                    id = "menu",
                    label = string.empty,
                    icon = Res.drawable.ic_menu_expanded
                ),
                onClick = {
                    expanded.value = !expanded.value
                },
                isExpanded = expanded.value
            )
        }
    }
}

@Composable
private fun NavigationItem(
    item: NavigationItem,
    isExpanded: Boolean,
    onClick: ((NavigationItem) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = animateColorAsState(targetValue = when (item.isSelected) {
        true -> AppTheme.colors.primary.copy(alpha = 0.2f)
        else -> AppTheme.colors.surfaceNav
    }, label = "backgroundColor")
    val iconPadding = animateDpAsState(targetValue = when (isExpanded) {
        true -> AppTheme.dimens.medium
        false -> (itemSize - iconSize) / 2
    }, label = "iconPadding")

    Row(modifier = modifier
        .padding(
            horizontal = (columnWidthCollapsed - itemSize) / 2
        )
        .fillMaxWidth()
        .height(itemSize)
        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
        .background(backgroundColor.value)
        .clickable(
            enabled = onClick != null,
            onClick = {
                onClick?.invoke(item)
            }
        )
        .padding(
            horizontal = iconPadding.value,
        )
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.CenterVertically),
            painter = painterResource(resource = item.icon),
            tint = AppTheme.colors.onSurface,
            contentDescription = stringResource(resource = item.label)
        )
        if (isExpanded) {
            TextBody1(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.small)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                text = item.label.let { stringResource(resource = it) }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCompactLight() {
    AppThemePreview(isLight = true) {
        PreviewCompact()
    }
}

@Preview
@Composable
private fun PreviewCompactDark() {
    AppThemePreview(isLight = false) {
        PreviewCompact()
    }
}

@Preview
@Composable
private fun PreviewCompact() {
    NavigationColumn(
        lockExpanded = false,
        itemClicked = { },
        list = fakeNavigationItems
    )
}

@Preview
@Composable
private fun PreviewExpandedLight() {
    AppThemePreview(isLight = true) {
        PreviewExpanded()
    }
}

@Preview
@Composable
private fun PreviewExpandedDark() {
    AppThemePreview(isLight = false) {
        PreviewExpanded()
    }
}

@Preview
@Composable
private fun PreviewExpanded() {
    NavigationColumn(
        lockExpanded = true,
        itemClicked = { },
        list = fakeNavigationItems
    )
}
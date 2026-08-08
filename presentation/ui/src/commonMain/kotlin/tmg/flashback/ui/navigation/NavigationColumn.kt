package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.empty
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1

val columnWidthCollapsed: Dp = 64.dp
private val itemSize: Dp = 48.dp
private val iconSize: Dp = 24.dp
val columnWidthExpanded: Dp = 200.dp

@Composable
fun NavigationColumn(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    lockExpanded: Boolean = false,
) {
    NavigationColumn(
        primary = list,
        divider = { },
        secondary = emptyList(),
        tertiary = emptyList(),
        itemClicked = itemClicked,
        modifier = modifier,
        padding = padding,
        lockExpanded = lockExpanded
    )
}

@Composable
fun NavigationColumn(
    primary: List<NavigationItem>,
    divider: @Composable () -> Unit,
    secondary: List<NavigationItem>,
    tertiary: List<NavigationItem> = emptyList(),
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    lockExpanded: Boolean = false,
) {
    val expanded = remember { mutableStateOf(lockExpanded) }
    val width = animateDpAsState(targetValue = when (expanded.value) {
        true -> columnWidthExpanded
        false -> columnWidthCollapsed
    }, label = "width")

    Column(modifier = modifier
        .width(width.value)
        .fillMaxHeight()
        .padding(
            vertical = AppTheme.dimens.small
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = padding,
            content = {
                if (!lockExpanded) {
                    item {
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
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(AppTheme.dimens.small))
                    }
                }
                items(primary) {
                    NavigationItem(
                        item = it,
                        isExpanded = expanded.value,
                        onClick = itemClicked,
                    )
                    Spacer(Modifier.height(AppTheme.dimens.small))
                }
                item {
                    divider()
                }
                items(secondary) {
                    NavigationItem(
                        item = it,
                        isExpanded = expanded.value,
                        onClick = itemClicked,
                    )
                    Spacer(Modifier.height(AppTheme.dimens.small))
                }
                item {
                    divider()
                }
                items(tertiary) {
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
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        else -> Color.Transparent
    }, label = "backgroundColor")
    val contentColor = animateColorAsState(targetValue = when (item.isSelected) {
        true -> AppTheme.colors.onPrimaryContainer
        else -> AppTheme.colors.onSurface
    })
    val iconPadding = animateDpAsState(targetValue = when (isExpanded) {
        true -> AppTheme.dimens.medium
        false -> (itemSize - iconSize) / 2
    }, label = "iconPadding")
    val iconTransition = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0f
    }, label = "iconTransition")

    Row(modifier = modifier
        .padding(
            horizontal = (columnWidthCollapsed - itemSize) / 2
        )
        .fillMaxWidth()
        .height(itemSize)
        .clip(RoundedCornerShape(100.dp))
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
        val contentDescription = stringResource(item.label)
        Box(modifier = Modifier
            .size(iconSize)
            .clearAndSetSemantics {
                this.contentDescription = contentDescription
            }
            .align(Alignment.CenterVertically)
        ) {
            Icon(
                modifier = Modifier
                    .alpha(1f - iconTransition.value)
                    .size(iconSize),
                painter = painterResource(resource = item.icon),
                tint = contentColor.value,
                contentDescription = stringResource(resource = item.label)
            )
            Icon(
                modifier = Modifier
                    .alpha(iconTransition.value)
                    .size(iconSize),
                painter = painterResource(resource = item.selectedIcon),
                tint = contentColor.value,
                contentDescription = stringResource(resource = item.label)
            )
        }
        if (isExpanded) {
            TextBody1(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.small)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                maxLines = 1,
                textColor = contentColor.value,
                text = stringResource(resource = item.label)
            )
        }
    }
}

@PreviewTheme
@Composable
private fun PreviewCompact() {
    ApplicationThemePreview() {
        NavigationColumn(
            lockExpanded = false,
            itemClicked = { },
            list = fakeNavigationItems
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewExpanded() {
    ApplicationThemePreview() {
        NavigationColumn(
            lockExpanded = true,
            itemClicked = { },
            list = fakeNavigationItems
        )
    }
}
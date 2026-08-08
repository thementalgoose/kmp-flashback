package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

val navigationOrbiterColumnWidth: Dp = 64.dp
private val itemSize: Dp = 48.dp
private val iconSize: Dp = 24.dp

@Composable
fun NavigationOrbiter(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationOrbiter(
        primary = list,
        divider = { },
        secondary = emptyList(),
        tertiary = emptyList(),
        itemClicked = itemClicked,
        modifier = modifier
    )
}
@Composable
fun NavigationOrbiter(
    primary: List<NavigationItem>,
    divider: @Composable () -> Unit,
    secondary: List<NavigationItem>,
    tertiary: List<NavigationItem> = emptyList(),
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .background(AppTheme.colors.surfaceNav)
            .width(navigationOrbiterColumnWidth)
            .padding(
                vertical = AppTheme.dimens.small
            ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
    ) {
        primary.forEach { item ->
            NavigationItem(
                item = item,
                onClick = itemClicked,
            )
        }
        divider()
        secondary.forEach { item ->
            NavigationItem(
                item = item,
                onClick = itemClicked,
            )
        }
        divider()
        tertiary.forEach { item ->
            NavigationItem(
                item = item,
                onClick = itemClicked,
            )
        }
    }
}

@Composable
private fun NavigationItem(
    item: NavigationItem,
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
            horizontal = (itemSize - iconSize) / 2,
        )
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.CenterVertically),
            painter = painterResource(resource = item.icon),
            tint = contentColor.value,
            contentDescription = stringResource(resource = item.label)
        )
    }
}


@PreviewTheme
@Composable
private fun PreviewList() {
    ApplicationThemePreview {
        NavigationOrbiter(
            itemClicked = { },
            list = fakeNavigationItems
        )
    }
}

@Preview
@Composable
private fun PreviewWithDivider() {
    ApplicationThemePreview {
        NavigationOrbiter(
            divider = {
                HorizontalDivider(
                    modifier = Modifier.padding(
                        vertical = AppTheme.dimens.small,
                        horizontal = AppTheme.dimens.medium
                    )
                )
            },
            itemClicked = { },
            primary = fakeNavigationItems.take(3),
            secondary = fakeNavigationItems.take(1),
            tertiary = fakeNavigationItems.takeLast(1)
        )
    }
}
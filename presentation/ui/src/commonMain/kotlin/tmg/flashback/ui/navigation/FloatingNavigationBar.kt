package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2

private val edgePadding: Dp = 6.dp
private val iconSize: Dp = 24.dp
private val horizontalWidthThreshold: Dp = 180.dp

@Composable
fun FloatingNavigationBar(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    shadow: Dp = 8.dp,
    showLabels: Boolean = false,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .shadow(shadow, shape = RoundedCornerShape(100.dp))
            .clip(RoundedCornerShape(100.dp))
            .background(AppTheme.colors.surfaceNav)
    ) {
        val displayAsVertical = (horizontalWidthThreshold * list.size) > minWidth
        Row(
            modifier = Modifier
                .background(AppTheme.colors.surfaceNav)
                .padding(horizontal = edgePadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (displayAsVertical) {
                list.forEach { item ->
                    VerticalItem(
                        item = item,
                        itemClicked = itemClicked,
                        modifier = Modifier.weight(1f),
                        showLabel = showLabels
                    )
                }
            } else {
                list.forEach { item ->
                    HorizontalItem(
                        item = item,
                        itemClicked = itemClicked,
                        modifier = Modifier.weight(1f),
                        showLabel = showLabels
                    )
                }
            }
        }
    }
}

@Composable
private fun VerticalItem(
    item: NavigationItem,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    showLabel: Boolean
) {
    val backgroundColor = animateColorAsState(targetValue = when (item.isSelected ?: false) {
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        false -> Color.Transparent
    }, label = "backgroundColor")
    val fractionWidth = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0.5f
    }, label = "fractionWidth")
    val fractionHeight = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0.8f
    }, label = "fractionWidth")
    Box(
        modifier = modifier
            .padding(vertical = edgePadding)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = { itemClicked(item) })
    ) {
        Box(Modifier
            .align(Alignment.Center)
            .fillMaxWidth(fractionWidth.value)
            .fillMaxHeight(fractionHeight.value)
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor.value)
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    vertical = if (showLabel) AppTheme.dimens.xsmall else AppTheme.dimens.small,
                    horizontal = AppTheme.dimens.xsmall
                ),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(resource = item.icon),
                tint = AppTheme.colors.onSurface,
                contentDescription = null,
            )
            if (showLabel) {
                TextBody2(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 4.dp),
                    text = stringResource(item.label),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun HorizontalItem(
    item: NavigationItem,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    showLabel: Boolean
) {
    val backgroundColor = animateColorAsState(targetValue = when (item.isSelected ?: false) {
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        false -> Color.Transparent
    }, label = "backgroundColor")
    val fractionWidth = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0.5f
    }, label = "fractionWidth")
    val fractionHeight = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0.8f
    }, label = "fractionWidth")
    Box(
        modifier = modifier
            .padding(vertical = edgePadding)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = { itemClicked(item) }),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth(fractionWidth.value)
                .fillMaxHeight(fractionHeight.value)
                .clip(RoundedCornerShape(100.dp))
                .background(backgroundColor.value)
        )
        Row(
            modifier = modifier
                .padding(all = AppTheme.dimens.xsmall)
                .clickable(onClick = { itemClicked(item) }),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize),
                painter = painterResource(resource = item.icon),
                tint = AppTheme.colors.onSurface,
                contentDescription = null,
            )
            if (showLabel) {
                TextBody2(
                    text = stringResource(item.label),
                    maxLines = 1,
                )
            }
        }
    }
}

@PreviewTheme
@Composable
private fun Preview5() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 5, showLabels = false)
            Preview(itemCount = 5, showLabels = true)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview4() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 4, showLabels = false)
            Preview(itemCount = 4, showLabels = true)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview3() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 3, showLabels = false)
            Preview(itemCount = 3, showLabels = true)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview2() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 2, showLabels = false)
            Preview(itemCount = 2, showLabels = true)
        }
    }
}

@Composable
private fun Preview(
    itemCount: Int,
    showLabels: Boolean
) {
    Box(
        modifier = Modifier
            .background(AppTheme.colors.surfaceContainer5)
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        FloatingNavigationBar(
            list = fakeNavigationItems.take(itemCount),
            itemClicked = { },
            showLabels = showLabels
        )
    }
}

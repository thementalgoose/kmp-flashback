@file:OptIn(ExperimentalMaterial3Api::class)

package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody2

private val edgePadding: Dp = 8.dp
private val iconVerticalPadding: Dp = 10.dp
private val iconSize: Dp = 26.dp
private val horizontalWidthThreshold: Dp = 180.dp
val appBarHeight: Dp by lazy {
    iconSize + (2 * edgePadding) + (2 * iconVerticalPadding)
}

@Composable
fun FloatingNavigationBar(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(bottom = bottomPadding)
            .fillMaxWidth()
            .height(appBarHeight)
            .dropShadow(
                shape = RoundedCornerShape(100.dp),
                shadow = Shadow(radius = 16.dp, color = Color.Black.copy(alpha = 0.2f))
            )
            .clip(RoundedCornerShape(100.dp))
            .background(AppTheme.colors.surfaceNav)
    ) {
        val showLabels = (horizontalWidthThreshold * list.size) < minWidth
        Row(
            modifier = Modifier
                .background(AppTheme.colors.surfaceNav)
                .padding(horizontal = edgePadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            list.forEach { item ->
                Item(
                    item = item,
                    itemClicked = itemClicked,
                    modifier = Modifier.weight(1f),
                    showLabel = showLabels
                )
            }
        }
    }
}

@Composable
private fun Item(
    item: NavigationItem,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    showLabel: Boolean
) {
    val backgroundColor = animateColorAsState(targetValue = when (item.isSelected ?: false) {
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        false -> Color.Transparent
    }, label = "backgroundColor")
    val textColor = animateColorAsState(targetValue = when (item.isSelected ?: false) {
        true -> AppTheme.colors.onPrimaryContainer
        false -> AppTheme.colors.onSurface
    }, label = "backgroundColor")
    val fractionWidth = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0.5f
    }, label = "fractionWidth")
    val fractionHeight = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0.8f
    }, label = "fractionWidth")
    val iconTransition = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0f
    })

    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    val labelText = stringResource(item.label)
    val tooltip = TooltipDefaults.rememberTooltipPositionProvider(
        positioning = TooltipAnchorPosition.Above,
        spacingBetweenTooltipAndAnchor = AppTheme.dimens.large
    )

    Box(
        modifier = modifier
            .padding(vertical = edgePadding)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(100.dp))
            .combinedClickable(
                onClick = { itemClicked(item) },
                onLongClick = {
                    scope.launch {
                        tooltipState.show()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        TooltipBox(
            positionProvider = tooltip,
            tooltip = {
                PlainTooltip {
                    Text(labelText)
                }
            },
            state = tooltipState,
            modifier = Modifier.matchParentSize(),
            content = { }
        )
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
                .padding(vertical = iconVerticalPadding),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(iconSize)
            ) {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .alpha(1f - iconTransition.value),
                    painter = painterResource(resource = item.icon),
                    tint = AppTheme.colors.onSurface,
                    contentDescription = null,
                )
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .alpha(iconTransition.value),
                    painter = painterResource(resource = item.selectedIcon),
                    tint = AppTheme.colors.onPrimaryContainer,
                    contentDescription = null,
                )
            }
            if (showLabel) {
                TextBody2(
                    bold = true,
                    textColor = textColor.value,
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
            Preview(itemCount = 5)
            Preview(itemCount = 5)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview4() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 4)
            Preview(itemCount = 4)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview3() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 3)
            Preview(itemCount = 3)
        }
    }
}

@PreviewTheme
@Composable
private fun Preview2() {
    ApplicationThemePreview {
        Column {
            Preview(itemCount = 2)
            Preview(itemCount = 2)
        }
    }
}

@Composable
private fun Preview(
    itemCount: Int
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
        )
    }
}

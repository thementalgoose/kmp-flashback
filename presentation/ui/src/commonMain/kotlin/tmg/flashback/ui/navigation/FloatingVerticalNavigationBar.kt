@file:OptIn(ExperimentalMaterial3Api::class)

package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

private val edgePadding: Dp = 8.dp
private val iconSpacing: Dp = 12.dp
private val iconSize: Dp = 26.dp

val floatingVerticalAppWidth: Dp by lazy {
    iconSize + (2 * edgePadding) + (2 * iconSpacing)
}

@Composable
fun FloatingVerticalNavigationBar(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .width(floatingVerticalAppWidth)
            .dropShadow(
                shape = RoundedCornerShape(100.dp),
                shadow = Shadow(radius = 16.dp, color = Color.Black.copy(alpha = 0.2f))
            )
            .clip(RoundedCornerShape(100.dp))
            .background(AppTheme.colors.surfaceNav)
    ) {
        Column(
            modifier = Modifier
                .background(AppTheme.colors.surfaceNav)
                .padding(horizontal = edgePadding),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            list.forEach { item ->
                Item(
                    item = item,
                    itemClicked = itemClicked,
                )
            }
        }
    }
}

@Composable
private fun Item(
    item: NavigationItem,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
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
    val iconTransition = animateFloatAsState(targetValue = when (item.isSelected ?: false) {
        true -> 1f
        false -> 0f
    })

    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    val labelText = stringResource(item.label)
    val tooltip = TooltipDefaults.rememberTooltipPositionProvider(
        positioning = TooltipAnchorPosition.Left,
        spacingBetweenTooltipAndAnchor = AppTheme.dimens.large
    )

    Box(
        modifier = modifier
            .padding(vertical = edgePadding)
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(100.dp))
            .semantics {
                role = Role.Tab
                selected = item.isSelected == true
            }
            .combinedClickable(
                role = Role.Tab,
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
        Column(
            modifier = modifier
                .padding(all = iconSpacing),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally
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
        }
    }
}


@PreviewTheme
@Composable
private fun Preview5() {
    ApplicationThemePreview {
        Column {
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
        }
    }
}

@PreviewTheme
@Composable
private fun Preview3() {
    ApplicationThemePreview {
        Column {
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
            .padding(all = 16.dp)
    ) {
        FloatingVerticalNavigationBar(
            list = fakeNavigationItems.take(itemCount),
            itemClicked = { },
        )
    }
}

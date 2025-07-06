package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.text.TextBody1

private val verticalSelectedPillWidth: Dp = 64.dp
private val verticalPillHeight: Dp = 32.dp
private val horizontalWidthThreshold: Dp = 180.dp
private val iconSize: Dp = 24.dp
val appBarHeightWhenVertical: Dp = 80.dp
val appBarHeightWhenHorizontal: Dp = 60.dp

val appBarMaximumHeight = max(appBarHeightWhenVertical, appBarHeightWhenHorizontal)

@Composable
fun NavigationBar(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp)
            .background(AppTheme.colors.surfaceNav)
            .padding(bottom = bottomPadding)
    ) {
        val displayAsVertical = (horizontalWidthThreshold * list.size) > minWidth
        val appBarHeight = animateDpAsState(
            targetValue = if (displayAsVertical) appBarHeightWhenVertical else appBarHeightWhenHorizontal
        )
        Row(
            modifier = Modifier
                .height(appBarHeight.value)
                .background(AppTheme.colors.surfaceNav),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (displayAsVertical) {
                // Vertical Items
                list.forEach { item ->
                    VerticalItem(
                        item = item,
                        itemClicked = itemClicked,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                // Horizontal Items
                list.forEach { item ->
                    HorizontalItem(
                        item = item,
                        itemClicked = itemClicked,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun HorizontalItem(
    item: NavigationItem,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = animateColorAsState(targetValue = when (item.isSelected ?: false) {
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        false -> Color.Transparent
    }, label = "backgroundColor")
    Row(
        modifier = modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor.value)
            .height(iconSize + (AppTheme.dimens.small * 2))
            .combinedClickable(
                onClick = {
                    if (item.isSelected != true) {
                        itemClicked(item)
                    }
                }
            )
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(
                    vertical = AppTheme.dimens.small,
                    horizontal = AppTheme.dimens.medium
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
            TextBody1(
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                    ),
                text = stringResource(resource = item.label),
                textColor = AppTheme.colors.onSurface,
                bold = item.isSelected == true
            )
        }
    }
}

@Composable
private fun VerticalItem(
    item: NavigationItem,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedWidth = animateDpAsState(targetValue = when (item.isSelected ?: false) {
        true -> verticalSelectedPillWidth
        false -> iconSize
    }, label = "selectedWidth")
    val selectedX = animateDpAsState(targetValue = when (item.isSelected ?: false) {
        true -> 0.dp
        false -> (verticalSelectedPillWidth - iconSize) / 2
    }, label = "selectedX")
    val backgroundColor = animateColorAsState(targetValue = when (item.isSelected ?: false) {
        true -> AppTheme.colors.primary.copy(alpha = 0.3f)
        false -> Color.Transparent
    }, label = "backgroundColor")

    Column(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    if (item.isSelected != true) {
                        itemClicked(item)
                    }
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier
            .width(verticalSelectedPillWidth)
            .height(verticalPillHeight)
        ) {
            Box(modifier = Modifier
                .width(selectedWidth.value)
                .height(verticalPillHeight)
                .align(Alignment.CenterStart)
                .offset(x = selectedX.value)
                .clip(RoundedCornerShape(verticalPillHeight / 2))
                .background(backgroundColor.value))
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.Center),
                painter = painterResource(resource = item.icon),
                tint = AppTheme.colors.onSurface,
                contentDescription = null,
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            TextBody1(
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 10.dp,
                        start = 4.dp,
                        end = 4.dp
                    ),
                text = stringResource(resource = item.label),
                textColor = AppTheme.colors.onSurface,
                bold = item.isSelected == true
            )
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(AppTheme.dimens.small)
                    .align(Alignment.CenterEnd)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Transparent,
                                AppTheme.colors.surfaceNav
                            )
                        )
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    ApplicationThemePreview(isLight = true) {
        PreviewNoPadding()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    ApplicationThemePreview(isLight = false) {
        PreviewNoPadding()
    }
}

@Composable
private fun PreviewNoPadding() {
    Box(Modifier.padding(16.dp)) {
        NavigationBar(
            list = fakeNavigationItems,
            itemClicked = {}
        )
    }
}

@Preview
@Composable
private fun PreviewFewLight() {
    ApplicationThemePreview(isLight = true) {
        PreviewFew()
    }
}

@Preview
@Composable
private fun PreviewFewDark() {
    ApplicationThemePreview(isLight = false) {
        PreviewFew()
    }
}

@Composable
private fun PreviewFew() {
    Box(Modifier.padding(16.dp)) {
        NavigationBar(
            list = fakeNavigationItems.take(2),
            itemClicked = {}
        )
    }
}

@Preview
@Composable
private fun PreviewWithBottomPaddingLight() {
    ApplicationThemePreview(isLight = true) {
        PreviewWithBottomPadding()
    }
}

@Preview
@Composable
private fun PreviewWithBottomPaddingDark() {
    ApplicationThemePreview(isLight = false) {
        PreviewWithBottomPadding()
    }
}

@Composable
private fun PreviewWithBottomPadding() {
    Box(Modifier.padding(16.dp)) {
        NavigationBar(
            bottomPadding = 10.dp,
            list = fakeNavigationItems.take(3),
            itemClicked = {}
        )
    }
}
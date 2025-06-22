package tmg.flashback.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextBody1

private val selectedPillWidth: Dp = 64.dp
private val pillHeight: Dp = 32.dp
private val iconSize: Dp = 24.dp
val appBarHeight: Dp = 80.dp

@Composable
fun NavigationBar(
    list: List<NavigationItem>,
    itemClicked: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp
) {
    Column(modifier
        .shadow(8.dp)
        .background(AppTheme.colors.surfaceNav)
        .padding(bottom = bottomPadding)
    ) {
        Row(
            modifier = Modifier
                .height(appBarHeight)
                .background(AppTheme.colors.surfaceNav),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            list.forEach { item ->
                Item(
                    item = item,
                    itemClicked = itemClicked,
                    modifier = Modifier.weight(1f)
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
    val selectedWidth = animateDpAsState(targetValue = when (item.isSelected ?: false) {
        true -> selectedPillWidth
        false -> iconSize
    }, label = "selectedWidth")
    val selectedX = animateDpAsState(targetValue = when (item.isSelected ?: false) {
        true -> 0.dp
        false -> (selectedPillWidth - iconSize) / 2
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
            .width(selectedPillWidth)
            .height(pillHeight)
        ) {
            Box(modifier = Modifier
                .width(selectedWidth.value)
                .height(pillHeight)
                .align(Alignment.CenterStart)
                .offset(x = selectedX.value)
                .clip(RoundedCornerShape(pillHeight / 2))
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
    AppThemePreview(isLight = true) {
        Preview()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    AppThemePreview(isLight = false) {
        Preview()
    }
}

@Composable
private fun Preview() {
    Box(Modifier.padding(16.dp)) {
        NavigationBar(
            list = fakeNavigationItems,
            itemClicked = {}
        )
    }
}

@Preview
@Composable
private fun PreviewWithBottomPaddingLight() {
    AppThemePreview(isLight = true) {
        PreviewWithBottomPadding()
    }
}

@Preview
@Composable
private fun PreviewWithBottomPaddingDark() {
    AppThemePreview(isLight = false) {
        PreviewWithBottomPadding()
    }
}

@Composable
private fun PreviewWithBottomPadding() {
    Box(Modifier.padding(16.dp)) {
        NavigationBar(
            bottomPadding = 10.dp,
            list = fakeNavigationItems,
            itemClicked = {}
        )
    }
}
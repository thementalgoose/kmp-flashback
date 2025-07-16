package tmg.flashback.style.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.preview
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppTheme.disabledAlpha
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2

@Composable
fun Segments(
    items: List<ButtonItem>,
    selected: ButtonItem?,
    segmentClicked: (ButtonItem) -> Unit,
    modifier: Modifier = Modifier,
    showTick: Boolean = false,
    enabled: Boolean = true
) {
    Segments(
        items = items,
        key = selected?.key,
        segmentClicked = segmentClicked,
        modifier = modifier,
        showTick = showTick,
        enabled = enabled
    )
}
@Composable
fun Segments(
    items: List<ButtonItem>,
    key: String?,
    segmentClicked: (ButtonItem) -> Unit,
    modifier: Modifier = Modifier,
    showTick: Boolean = false,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .alpha(if (enabled) 1f else disabledAlpha)
                .border(
                    width = 1.dp,
                    color = AppTheme.colors.outline,
                    shape = RoundedCornerShape(AppTheme.dimens.radiusMedium)
                )
        ) {
            items.forEachIndexed { index, item ->
                val selected = item.key == key
                SegmentButton(
                    item = item,
                    selected = selected,
                    showTick = showTick,
                    enabled = enabled,
                    onClick = segmentClicked,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clip(
                            when (index) {
                                0 -> RoundedCornerShape(topStart = AppTheme.dimens.radiusMedium, bottomStart = AppTheme.dimens.radiusMedium)
                                items.size - 1 -> RoundedCornerShape(topEnd = AppTheme.dimens.radiusMedium, bottomEnd = AppTheme.dimens.radiusMedium)
                                else -> RoundedCornerShape(0.dp)
                            }
                        )
                )
                if (index != items.size - 1) {
                    Spacer(Modifier
                        .fillMaxHeight()
                        .background(AppTheme.colors.outline)
                        .padding(vertical = 1.dp)
                        .width(1.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SegmentButton(
    item: ButtonItem,
    selected: Boolean,
    enabled: Boolean,
    showTick: Boolean,
    onClick: (ButtonItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = when (selected) {
        true -> AppTheme.colors.tertiaryContainer
        false -> AppTheme.colors.surface
    }
    Row(
        modifier = modifier
            .background(background)
            .clickable { onClick(item) }
            .padding(
                horizontal = AppTheme.dimens.xsmall,
                vertical = AppTheme.dimens.small
            )
            .focusable(true),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextBody2(
            stringResource(resource = item.string),
            maxLines = 1,
            bold = selected,
            modifier = Modifier
        )
        if (showTick) {
            Box(Modifier.height(16.dp).width(0.dp))
            AnimatedVisibility(visible = selected) {
                Row {
                    Spacer(Modifier.width(AppTheme.dimens.small))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = AppTheme.colors.onSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private val fakeItems = listOf(
    ButtonItem(key = "1", string = Res.string.preview),
    ButtonItem(key = "2", string = Res.string.preview),
    ButtonItem(key = "3", string = Res.string.preview)
)

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider ::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            Segments(
                items = fakeItems,
                selected = fakeItems.first(),
                segmentClicked = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewShowTick(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            Segments(
                items = fakeItems,
                selected = fakeItems.first(),
                segmentClicked = { },
                showTick = true,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDisabled(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            Segments(
                items = fakeItems,
                selected = fakeItems.first(),
                segmentClicked = { },
                showTick = true,
                enabled = false
            )
        }
    }
}
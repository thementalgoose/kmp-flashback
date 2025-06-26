package tmg.flashback.feature.weekend.presentation.weekend.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import flashback.feature.weekend.generated.resources.Res
import flashback.feature.weekend.generated.resources.ic_tick
import flashback.presentation.ui.generated.resources.arrow_down
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextTitle

@Composable
fun CollapsibleBox(
    title: String,
    modifier: Modifier = Modifier,
    showCheckmark: Boolean = false,
    isExpanded: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    val radius = animateDpAsState(
        targetValue = if (isExpanded.value) 0.dp else AppTheme.dimens.radiusMedium,
        label = "rotation"
    )
    Column(
        modifier = modifier
            .padding(
                top = AppTheme.dimens.medium,
                start = AppTheme.dimens.medium,
                end = AppTheme.dimens.medium
            )
            .clip(
                RoundedCornerShape(
                    topStart = AppTheme.dimens.radiusMedium,
                    topEnd = AppTheme.dimens.radiusMedium,
                    bottomStart = radius.value,
                    bottomEnd = radius.value,
                )
            )
            .background(AppTheme.colors.surfaceContainer3)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded.value = !isExpanded.value
                }
                .padding(
                    horizontal = AppTheme.dimens.medium,
                    vertical = AppTheme.dimens.small
                )
        ) {
            TextTitle(
                text = title,
                modifier = Modifier.weight(1f)
            )
            val degrees = animateFloatAsState(
                targetValue = if (isExpanded.value) 0f else 90f,
                label = "rotation"
            )
            if (showCheckmark) {
                Icon(
                    painter = painterResource(resource = Res.drawable.ic_tick),
                    contentDescription = null,
                    tint = AppTheme.colors.f1ResultsFull
                )
            }
            Icon(
                modifier = Modifier.rotate(degrees.value),
                painter = painterResource(resource = flashback.presentation.ui.generated.resources.Res.drawable.arrow_down),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CollapsibleBox(
        title = "Qualifying"
    )
}
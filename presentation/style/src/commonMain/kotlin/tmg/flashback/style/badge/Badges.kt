package tmg.flashback.style.badge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2

data class Badge(
    val label: String,
    val icon: DrawableResource? = null,
)

@Composable
fun BadgesView(
    list: List<Badge>,
    modifier: Modifier = Modifier,
    spacing: Dp = AppTheme.dimens.small
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        list.forEach {
            BadgeView(model = it)
        }
    }
}

@Composable
fun BadgeView(
    model: Badge,
    modifier: Modifier = Modifier,
    tintIcon: Color? = AppTheme.colors.onSurface,
) {
    Row(modifier = modifier
        .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
        .background(AppTheme.colors.surfaceContainer2)
        .border(
            1.dp,
            color = AppTheme.colors.outline.copy(alpha = 0.35f),
            shape = RoundedCornerShape(AppTheme.dimens.radiusSmall)
        )
        .padding(
            horizontal = AppTheme.dimens.small,
            vertical = AppTheme.dimens.xsmall
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (model.icon != null) {
            if (tintIcon != null) {
                Icon(
                    painter = painterResource(resource = model.icon),
                    contentDescription = null,
                    tint = tintIcon,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Image(
                    painter = painterResource(resource = model.icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(Modifier.width(6.dp))
        }
        TextBody2(
            text = model.label,
            bold = true
        )
    }
}

@Preview
@Composable
private fun PreviewList(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        BadgesView(list = listOf(fakeMenuBadge, fakeBackIconBadge))
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        BadgeView(fakeBackBadge)
    }
}

private val fakeMenuBadge = Badge(
    label = "Play"
)
private val fakeMenuIconBadge = Badge(
    label = "Pause",
//    icon = Res.drawable.
)
private val fakeBackBadge = Badge(
    label = "Play"
)
private val fakeBackIconBadge = Badge(
    label = "Pause",
//    icon = androidx.core.R.drawable.ic_call_answer_low
)
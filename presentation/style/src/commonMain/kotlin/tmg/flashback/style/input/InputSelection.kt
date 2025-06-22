package tmg.flashback.style.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.ic_preview_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.text.TextBody1

@Composable
fun InputSelection(
    label: String,
    icon: DrawableResource,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isChecked: Boolean? = null,
    itemClicked: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .clickable(
                enabled = itemClicked != null,
                onClick = itemClicked ?: {}
            )
            .background(
                if (isSelected) AppTheme.colors.tertiaryContainer else Color.Transparent
            )
            .border(1.dp, when (isSelected) {
                true -> AppTheme.colors.outline
                false -> Color.Transparent
            }, RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .padding(
                vertical = AppTheme.dimens.nsmall,
                horizontal = AppTheme.dimens.medium
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(resource = icon),
            contentDescription = null,
            tint = AppTheme.colors.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(AppTheme.dimens.nsmall))
        TextBody1(
            text = label,
            modifier = Modifier.weight(1f)
        )
        if (isChecked != null) {
            Spacer(Modifier.width(AppTheme.dimens.small))
            InputSwitch(
                isChecked = isChecked
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSwitchLight(
    @PreviewParameter(BooleanParamProvider::class) isSelected: Boolean
) {
    AppThemePreview(isLight = true) {
        Column {
            InputSelection(
                label = "Night mode",
                icon = Res.drawable.ic_preview_icon,
                isSelected = isSelected
            )
            Spacer(Modifier.height(16.dp))
            InputSelection(
                label = "Night mode",
                icon = Res.drawable.ic_preview_icon,
                isSelected = isSelected,
                isChecked = true
            )
            Spacer(Modifier.height(16.dp))
            InputSelection(
                label = "Night mode",
                icon = Res.drawable.ic_preview_icon,
                isSelected = isSelected,
                isChecked = false
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSwitchDark(
    @PreviewParameter(BooleanParamProvider::class) isSelected: Boolean
) {
    AppThemePreview(isLight = true) {
        Column {
            InputSelection(
                label = "Night mode",
                icon = Res.drawable.ic_preview_icon,
                isSelected = isSelected
            )
            Spacer(Modifier.height(16.dp))
            InputSelection(
                label = "Night mode",
                icon = Res.drawable.ic_preview_icon,
                isSelected = isSelected,
                isChecked = true
            )
            Spacer(Modifier.height(16.dp))
            InputSelection(
                label = "Night mode",
                icon = Res.drawable.ic_preview_icon,
                isSelected = isSelected,
                isChecked = false
            )
        }
    }
}

internal class BooleanParamProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}
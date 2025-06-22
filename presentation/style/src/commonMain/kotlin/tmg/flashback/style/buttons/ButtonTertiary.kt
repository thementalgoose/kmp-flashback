package tmg.flashback.style.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.ic_preview_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppTheme.disabledAlpha
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2

@Composable
fun ButtonTertiary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .focusable(true)
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(0.dp)
            .defaultMinSize(1.dp, 1.dp)
            .alpha(if (enabled) 1f else disabledAlpha),
        border = BorderStroke(1.dp, AppTheme.colors.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppTheme.colors.tertiary,
            contentColor = AppTheme.colors.onTertiary,
            disabledContainerColor = AppTheme.colors.tertiary,
            disabledContentColor = AppTheme.colors.onTertiary
        ),
        enabled = enabled,
        shape = RoundedCornerShape(AppTheme.dimens.radiusMedium),
        onClick = onClick
    ) {
        TextBody2(
            text,
            bold = true,
            textColor = AppTheme.colors.onTertiary,
            modifier = Modifier
                .padding(
                    vertical = AppTheme.dimens.xsmall,
                    horizontal = AppTheme.dimens.medium,
                )
        )
        if (icon != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(resource = icon),
                contentDescription = null,
                tint = AppTheme.colors.onTertiary
            )
            Spacer(Modifier.width(AppTheme.dimens.nsmall))
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            ButtonTertiary(
                text = "Tertiary Button",
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWithIcon(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            ButtonTertiary(
                text = "Tertiary Button",
                onClick = { },
                icon = Res.drawable.ic_preview_icon
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDisabled(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            ButtonTertiary(
                text = "Tertiary Button",
                onClick = { },
                enabled = false
            )
        }
    }
}
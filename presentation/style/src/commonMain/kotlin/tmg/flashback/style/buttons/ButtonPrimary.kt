package tmg.flashback.style.buttons

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppTheme.disabledAlpha
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider


@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    return Button(
        modifier = modifier
            .focusable(true),
        colors = ButtonDefaults.textButtonColors(
            containerColor = AppTheme.colors.primary,
            disabledContainerColor = AppTheme.colors.primary.copy(alpha = disabledAlpha),
            disabledContentColor = AppTheme.colors.onPrimary.copy(alpha = disabledAlpha),
            contentColor = AppTheme.colors.onPrimary
        ),
        enabled = enabled,
        shape = RoundedCornerShape(AppTheme.dimens.radiusMedium),
        onClick = onClick
    ) {
        Text(
            text,
            color = AppTheme.colors.onPrimary.copy(alpha = if (enabled) 1f else disabledAlpha),
            modifier = Modifier
                .padding(
                    horizontal = AppTheme.dimens.medium,
                    vertical = AppTheme.dimens.xsmall
                )
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            ButtonPrimary(
                text = "Primary Button",
                onClick = { }
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
            ButtonPrimary(
                enabled = false,
                text = "Primary Button",
                onClick = { }
            )
        }
    }
}
package tmg.flashback.style.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppTheme.disabledAlpha
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2

@Composable
fun ButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false
) {
    return Button(
        modifier = modifier
            .focusable(true),
        border = BorderStroke(1.dp, when (enabled) {
            true -> AppTheme.colors.outline
            false -> AppTheme.colors.outline.copy(alpha = disabledAlpha)
        }),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.secondary,
            contentColor = AppTheme.colors.onSecondary,
            disabledContainerColor = AppTheme.colors.secondary.copy(alpha = disabledAlpha),
            disabledContentColor = AppTheme.colors.onSecondary,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(AppTheme.dimens.radiusMedium),
        onClick = onClick
    ) {
        TextBody1(
            text,
            bold = true,
            textColor = when (enabled) {
                true -> AppTheme.colors.onSecondary
                false -> AppTheme.colors.onSecondary.copy(alpha = disabledAlpha)
            },
            modifier = Modifier
                .padding(
                    vertical = AppTheme.dimens.xsmall,
                    horizontal = AppTheme.dimens.small,
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
            ButtonSecondary(
                text = "Secondary Button",
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSelected(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            ButtonSecondary(
                text = "Secondary Button",
                onClick = { },
                selected = true
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
            ButtonSecondary(
                enabled = false,
                text = "Secondary Button",
                onClick = { }
            )
        }
    }
}
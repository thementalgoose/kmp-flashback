package tmg.flashback.style.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2

private val colour: Color
    @Composable
    get() = AppTheme.colors.contentPrimary

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
            .focusable(true)
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(0.dp)
            .defaultMinSize(1.dp, 1.dp),
        border = BorderStroke(1.dp, when (enabled) {
            true -> colour
            false -> colour.copy(alpha = 0.4f)
        }),
        colors = ButtonDefaults.textButtonColors(
            containerColor = when (selected) {
                true -> AppTheme.colors.backgroundTertiary
                false -> AppTheme.colors.backgroundPrimary
            },
            contentColor = colour
        ),
        contentPadding = PaddingValues(),
        enabled = enabled,
        shape = CircleShape,
        onClick = onClick
    ) {
        TextBody2(
            text,
            bold = true,
            textColor = when (enabled) {
                true -> colour
                false -> colour.copy(alpha = 0.4f)
            },
            modifier = Modifier
                .padding(
                    start = AppTheme.dimens.medium,
                    top = AppTheme.dimens.small,
                    end = AppTheme.dimens.medium,
                    bottom = AppTheme.dimens.small
                )
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
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
    AppThemePreview(previewConfig) {
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
    AppThemePreview(previewConfig) {
        Box(Modifier.padding(16.dp)) {
            ButtonSecondary(
                enabled = false,
                text = "Secondary Button",
                onClick = { }
            )
        }
    }
}
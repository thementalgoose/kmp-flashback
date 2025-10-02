package tmg.flashback.ui.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_close
import flashback.presentation.localisation.generated.resources.settings_notifications_runtime_description
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_close
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1

@Composable
fun MessageBanner(
    label: StringResource,
    modifier: Modifier = Modifier,
    clicked: (() -> Unit)? = null,
    close: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.tertiaryContainer)
            .clickable(
                enabled = clicked != null,
                onClick = {
                    clicked?.invoke()
                }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextBody1(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = AppTheme.dimens.nsmall,
                    vertical = AppTheme.dimens.small
                ),
            text = stringResource(label),
            textColor = AppTheme.colors.onTertiaryContainer
        )
        if (close != null) {
            IconButton(
                onClick = close,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = stringResource(string.ab_close),
                        tint = AppTheme.colors.onTertiaryContainer,
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        MessageBanner(
            label = string.settings_notifications_runtime_description,
            clicked = { },
            close = { }
        )
    }
}

@Preview
@Composable
private fun PreviewNoClose(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        MessageBanner(
            label = string.settings_notifications_runtime_description,
            clicked = { }
        )
    }
}
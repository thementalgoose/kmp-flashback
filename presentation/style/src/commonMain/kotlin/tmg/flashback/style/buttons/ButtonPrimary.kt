package tmg.flashback.style.buttons

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.FlashbackTheme
import tmg.flashback.style.preview.PreviewDarkMode
import tmg.flashback.style.preview.PreviewLightMode


@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    return Button(
        modifier = modifier
            .focusable(true)
            .fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            containerColor = FlashbackTheme.colors.primary,
            disabledContainerColor = FlashbackTheme.colors.primary.copy(alpha = 0.4f),
            disabledContentColor = FlashbackTheme.colors.contentPrimaryInverse,
            contentColor = FlashbackTheme.colors.contentTertiaryInverse
        ),
        enabled = enabled,
        shape = CircleShape,
        onClick = onClick
    ) {
        Text(
            text,
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
private fun PreviewLight() = PreviewLightMode {
    Box(Modifier.padding(16.dp)) {
        ButtonPrimary(
            text = "Primary Button",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun PreviewDark() = PreviewDarkMode {
    Box(Modifier.padding(16.dp)) {
        ButtonPrimary(
            text = "Primary Button",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun PreviewDisabledLight() = PreviewLightMode {
    Box(Modifier.padding(16.dp)) {
        ButtonPrimary(
            enabled = false,
            text = "Primary Button",
            onClick = { }
        )
    }
}

@Preview
@Composable
private fun PreviewDisabledDark() = PreviewDarkMode {
    Box(Modifier.padding(16.dp)) {
        ButtonPrimary(
            enabled = false,
            text = "Primary Button",
            onClick = { }
        )
    }
}
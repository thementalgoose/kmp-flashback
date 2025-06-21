package tmg.flashback.ui.components.flag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import dev.carlsen.flagkit.FlagKit
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.SupportedTheme
import tmg.flashback.style.preview.PreviewConfig

@Composable
fun Flag(
    iso: String,
    modifier: Modifier = Modifier,
    nationality: String? = null,
) {
    val imageVector = FlagKit.getFlag(countryCode = iso)
    if (imageVector != null) {
        Image(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = nationality,
            contentScale = ContentScale.Fit,
        )
    } else {
        Box(
            modifier = modifier
                .background(AppTheme.colors.primary.copy(alpha = 0.3f))
                .clearAndSetSemantics {
                    contentDescription = nationality ?: iso
                }
        )
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(FlagProvider::class) flag: String
) {
    AppThemePreview {
        Flag(flag)
    }
}


class FlagProvider: PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "GB", "FR", "ES", "DK", "DE"
    )
}
package tmg.flashback.style.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.SupportedTheme

class PreviewConfigProvider: PreviewParameterProvider<PreviewConfig> {
    override val values: Sequence<PreviewConfig> = sequenceOf(
        PreviewConfig(theme = SupportedTheme.Default, isLightMode = true),
        PreviewConfig(theme = SupportedTheme.Default, isLightMode = false)
    )
}
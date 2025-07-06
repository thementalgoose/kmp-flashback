package tmg.flashback.style.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import tmg.flashback.style.theme.Theme

class PreviewConfigProvider: PreviewParameterProvider<PreviewConfig> {
    override val values: Sequence<PreviewConfig> = sequenceOf(
        PreviewConfig(theme = Theme.Default, isLightMode = true),
        PreviewConfig(theme = Theme.Default, isLightMode = false)
    )
}
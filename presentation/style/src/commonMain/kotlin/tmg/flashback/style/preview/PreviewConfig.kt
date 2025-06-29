package tmg.flashback.style.preview

import tmg.flashback.style.SupportedTheme

data class PreviewConfig(
    val theme: SupportedTheme,
    val isLightMode: Boolean
)
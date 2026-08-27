package tmg.flashback.feature.glossary.presentation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class GlossaryUiState(
    val searchTerm: String? = null,
    val entries: List<Entry> = emptyList()
)

data class Entry(
    val label: StringResource,
    val details: StringResource,
    val infographic: DrawableResource? = null
)

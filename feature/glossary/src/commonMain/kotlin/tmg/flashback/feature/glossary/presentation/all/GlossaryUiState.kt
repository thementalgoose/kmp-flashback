package tmg.flashback.feature.glossary.presentation.all

import tmg.flashback.formula1.constants.Glossary

data class GlossaryUiState(
    val searchTerm: String? = null,
    val entries: List<Glossary> = Glossary.entries
)

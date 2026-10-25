package tmg.flashback.composeApp.presentation.paddock

import tmg.flashback.composeApp.repositories.model.NavLink

data class PaddockUiState(
    val isRssEnabled: Boolean,
    val navLinks: List<NavLink>
)
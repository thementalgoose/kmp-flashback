package tmg.flashback.feature.weekend.presentation.weekend

sealed class WeekendUiState {
    data object Initial: WeekendUiState()

    data class Data(
        val season: Int,

    ): WeekendUiState()
}
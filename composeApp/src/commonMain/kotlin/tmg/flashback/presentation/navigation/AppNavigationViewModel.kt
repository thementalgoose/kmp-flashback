package tmg.flashback.presentation.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCase
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase

class AppNavigationViewModel(
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isMenuIconEnabledUseCase: IsMenuIconEnabledUseCase,
    isSnowEnabledUseCase: IsSnowEnabledUseCase,
    isSummerEnabledUseCase: IsSummerEnabledUseCase,
    isUkraineEnabledUseCase: IsUkraineEnabledUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<AppNavigationUIState> = MutableStateFlow(AppNavigationUIState(
        showRss = isRssEnabledUseCase(),
        menuIcon = isMenuIconEnabledUseCase(),
        snow = isSnowEnabledUseCase(),
        summer = isSummerEnabledUseCase(),
        ukraine = isUkraineEnabledUseCase()
    ))
    val uiState: StateFlow<AppNavigationUIState> = _uiState



}
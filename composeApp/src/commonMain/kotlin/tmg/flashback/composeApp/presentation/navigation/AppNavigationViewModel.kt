package tmg.flashback.composeApp.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.SavedState
import androidx.savedstate.read
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCase
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.composeApp.usecases.RequiresSyncUseCase

class AppNavigationViewModel(
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isMenuIconEnabledUseCase: IsMenuIconEnabledUseCase,
    isSnowEnabledUseCase: IsSnowEnabledUseCase,
    isSummerEnabledUseCase: IsSummerEnabledUseCase,
    isUkraineEnabledUseCase: IsUkraineEnabledUseCase,
    requiresSyncUseCase: RequiresSyncUseCase,
    maintenanceRepository: MaintenanceRepository,
    private val openStorePageUseCase: OpenStorePageUseCase
): ViewModel() {

    val easterEggs = AppNavigationEasterEggs(
        menuIcon = isMenuIconEnabledUseCase(),
        snow = isSnowEnabledUseCase(),
        summer = isSummerEnabledUseCase(),
        ukraine = isUkraineEnabledUseCase()
    )

    private val _uiState: MutableStateFlow<AppNavigationUIState> = MutableStateFlow(AppNavigationUIState(
        showRss = isRssEnabledUseCase(),
        easterEggs = easterEggs,
        screen = null,
        intoSubNavigation = false,
        promptContentSync = requiresSyncUseCase(),
        promptSoftUpgrade = maintenanceRepository.softUpgrade
    ))
    val uiState: StateFlow<AppNavigationUIState> = _uiState

    fun destinationUpdated(screen: NavKey?) {
        _uiState.update {
            it.copy(screen = screen)
        }
    }

    fun hideBar(hide: Boolean) {
        _uiState.update {
            it.copy(intoSubNavigation = hide)
        }
    }

    fun openStore() {
        _uiState.update {
            it.copy(promptSoftUpgrade = false)
        }
        openStorePageUseCase()
    }

    fun dismissSoftUpgrade() {
        _uiState.update {
            it.copy(promptSoftUpgrade = false)
        }
    }
}
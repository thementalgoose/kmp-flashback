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
import tmg.flashback.composeApp.repositories.NavRepository
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCase
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.composeApp.usecases.RequiresSyncUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.navigation.isList

class AppNavigationViewModel(
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isMenuIconEnabledUseCase: IsMenuIconEnabledUseCase,
    isSnowEnabledUseCase: IsSnowEnabledUseCase,
    isSummerEnabledUseCase: IsSummerEnabledUseCase,
    isUkraineEnabledUseCase: IsUkraineEnabledUseCase,
    requiresSyncUseCase: RequiresSyncUseCase,
    maintenanceRepository: MaintenanceRepository,
    navRepository: NavRepository,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val openStorePageUseCase: OpenStorePageUseCase,
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
        promptSoftUpgrade = maintenanceRepository.softUpgrade,
        extraLinks = navRepository.navLinks
    ))
    val uiState: StateFlow<AppNavigationUIState> = _uiState

    fun destinationUpdated(screen: NavKey?) {
        _uiState.update {
            it.copy(
                screen = screen,
                intoSubNavigation = screen?.isList() != true
            )
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

    fun openWebpage(url: String) {
        openWebpageUseCase(url)
    }
}
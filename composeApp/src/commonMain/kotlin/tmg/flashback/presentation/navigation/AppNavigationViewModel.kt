package tmg.flashback.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.savedstate.SavedState
import androidx.savedstate.read
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCase
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import tmg.flashback.infrastructure.log.logDebug
import tmg.flashback.navigation.Screen

class AppNavigationViewModel(
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isMenuIconEnabledUseCase: IsMenuIconEnabledUseCase,
    isSnowEnabledUseCase: IsSnowEnabledUseCase,
    isSummerEnabledUseCase: IsSummerEnabledUseCase,
    isUkraineEnabledUseCase: IsUkraineEnabledUseCase
): ViewModel(), NavController.OnDestinationChangedListener {

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
        intoSubNavigation = false
    ))
    val uiState: StateFlow<AppNavigationUIState> = _uiState

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: SavedState?
    ) {
        val screen: Screen? = when {
            destination.hasRoute<Screen.Calendar>() -> Screen.Calendar
            destination.hasRoute<Screen.DriverStandings>() -> Screen.DriverStandings
            destination.hasRoute<Screen.TeamStandings>() -> Screen.TeamStandings
            destination.hasRoute<Screen.Search>() -> Screen.Search
            destination.hasRoute<Screen.Rss>() -> Screen.Rss
            destination.hasRoute<Screen.ReactionGame>() -> Screen.ReactionGame
            destination.hasRoute<Screen.Settings>() -> Screen.Settings
            destination.hasRoute<Screen.About>() -> Screen.About
            destination.hasRoute<Screen.Circuit>() -> arguments?.read {
                val id = getString("id")
                val name = getString("name")
                return@read Screen.Circuit(id, name)
            }
            destination.hasRoute<Screen.Driver>() -> arguments?.read {
                val id = getString("id")
                val name = getString("name")
                return@read Screen.Driver(id, name)
            }
            destination.hasRoute<Screen.Constructor>() -> arguments?.read {
                val id = getString("id")
                val name = getString("name")
                return@read Screen.Constructor(id, name)
            }
            else -> null
        }

        logDebug("AppNavigation", "onDestination updated to $screen")
        _uiState.update {
            it.copy(screen = screen)
        }
    }

    fun hideBar(hide: Boolean) {
        _uiState.update {
            it.copy(intoSubNavigation = hide)
        }
    }
}
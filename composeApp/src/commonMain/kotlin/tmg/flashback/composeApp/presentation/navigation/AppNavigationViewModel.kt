package tmg.flashback.composeApp.presentation.navigation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tmg.flashback.composeApp.repositories.NavRepository
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.composeApp.usecases.RequiresSyncUseCase
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import org.koin.core.annotation.KoinViewModel
import tmg.flashback.navigation.Screen
import tmg.flashback.navigation.isList
import tmg.flashback.ui.toasts.Duration
import tmg.flashback.ui.toasts.ToastManager
import org.jetbrains.compose.resources.StringResource

@KoinViewModel
class AppNavigationViewModel(
    isRssEnabledUseCase: IsRssEnabledUseCase,
    isSnowEnabledUseCase: IsSnowEnabledUseCase,
    isSummerEnabledUseCase: IsSummerEnabledUseCase,
    requiresSyncUseCase: RequiresSyncUseCase,
    maintenanceRepository: MaintenanceRepository,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val openStorePageUseCase: OpenStorePageUseCase,
    private val toastManager: ToastManager,
): ViewModel() {

    fun updateToastColors(background: Color, foreground: Color) {
        toastManager.backgroundColor = background
        toastManager.foregroundColor = foreground
    }

    val easterEggs = AppNavigationEasterEggs(
        snow = isSnowEnabledUseCase(),
        summer = isSummerEnabledUseCase(),
    )

    private val _uiState: MutableStateFlow<AppNavigationUIState> = MutableStateFlow(AppNavigationUIState(
        showRss = isRssEnabledUseCase(),
        easterEggs = easterEggs,
        screen = null,
        intoSubNavigation = false,
        promptContentSync = requiresSyncUseCase(),
        promptSoftUpgrade = maintenanceRepository.softUpgrade,
        promptForceUpgrade = maintenanceRepository.forceUpgrade
    ))
    val uiState: StateFlow<AppNavigationUIState> = _uiState

    fun destinationUpdated(screen: Screen?) {
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

internal val previewAppNavigationViewModel = AppNavigationViewModel(
    isRssEnabledUseCase = object : IsRssEnabledUseCase {
        override fun invoke(): Boolean = true
    },
    isSnowEnabledUseCase = object : IsSnowEnabledUseCase {
        override fun invoke(): Boolean = false
    },
    isSummerEnabledUseCase = object : IsSummerEnabledUseCase {
        override fun invoke(): Boolean = false
    },
    requiresSyncUseCase = object : RequiresSyncUseCase {
        override fun invoke(): Boolean = false
    },
    maintenanceRepository = object : MaintenanceRepository {
        override val softUpgrade: Boolean get() = false
        override val forceUpgrade: Boolean get() = false
    },
    openWebpageUseCase = object : OpenWebpageUseCase {
        override fun invoke(url: String, title: String) {
            // No-op
        }
    },
    openStorePageUseCase = object : OpenStorePageUseCase {
        override fun invoke() {
            // No-op
        }
    },
    toastManager = object : ToastManager {
        override var backgroundColor: Color = Color.Unspecified
        override var foregroundColor: Color = Color.Unspecified
        override fun showMessage(message: String, duration: Duration) {}
        override fun showMessage(resourceId: StringResource, duration: Duration) {}
    },
)
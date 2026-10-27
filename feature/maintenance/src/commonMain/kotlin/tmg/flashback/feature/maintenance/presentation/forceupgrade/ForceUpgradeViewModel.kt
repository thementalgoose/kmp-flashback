package tmg.flashback.feature.maintenance.presentation.forceupgrade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.configuration.usecases.FetchConfigUseCase
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.LOCKED
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.UNLOCKED
import tmg.flashback.feature.maintenance.presentation.forceupgrade.ForceUpgradeUiState.VALIDATING
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository

class ForceUpgradeViewModel(
    private val fetchConfigUseCase: FetchConfigUseCase,
    private val maintenanceRepository: MaintenanceRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<ForceUpgradeUiState> = MutableStateFlow(maintenanceRepository.getState())
    val uiState: StateFlow<ForceUpgradeUiState> = _uiState

    fun MaintenanceRepository.getState(): ForceUpgradeUiState {
        return if (forceUpgrade) LOCKED else UNLOCKED
    }

    fun validate() {
        viewModelScope.launch {
            _uiState.update { VALIDATING }
            fetchConfigUseCase.fetchAndApply()
            _uiState.update { maintenanceRepository.getState() }
        }
    }
}
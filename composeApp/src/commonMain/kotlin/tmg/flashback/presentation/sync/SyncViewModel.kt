package tmg.flashback.presentation.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tmg.flashback.configuration.usecases.DoesConfigRequireSyncUseCase
import tmg.flashback.configuration.usecases.FetchConfigUseCase
import tmg.flashback.configuration.usecases.ResetConfigUseCase
import tmg.flashback.data.repo.model.Response
import tmg.flashback.data.repo.repository.CircuitRepository
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.feature.notifications.usecases.ScheduleUpcomingNotificationsUseCase
import tmg.flashback.presentation.sync.SyncState.DONE
import tmg.flashback.presentation.sync.SyncState.FAILED
import tmg.flashback.presentation.sync.SyncState.LOADING
import tmg.flashback.repositories.ContentSyncRepository

class SyncViewModel(
    private val circuitRepository: CircuitRepository,
    private val constructorRepository: ConstructorRepository,
    private val driverRepository: DriverRepository,
    private val overviewRepository: OverviewRepository,
    private val doesConfigRequireSyncUseCase: DoesConfigRequireSyncUseCase,
    private val resetConfigUseCase: ResetConfigUseCase,
    private val scheduleUpcomingNotificationsUseCase: ScheduleUpcomingNotificationsUseCase,
    private val fetchConfigRepository: FetchConfigUseCase,
    private val contentSyncRepository: ContentSyncRepository
): ViewModel() {

    private val _circuitsState: MutableStateFlow<SyncState> = MutableStateFlow(LOADING)
    val circuits: StateFlow<SyncState> = _circuitsState

    private val _constructorsState: MutableStateFlow<SyncState> = MutableStateFlow(LOADING)
    val constructors: StateFlow<SyncState> = _constructorsState

    private val _driversState: MutableStateFlow<SyncState> = MutableStateFlow(LOADING)
    val drivers: StateFlow<SyncState> = _driversState

    private val _racesState: MutableStateFlow<SyncState> = MutableStateFlow(LOADING)
    val races: StateFlow<SyncState> = _racesState

    private val _configState: MutableStateFlow<SyncState> = MutableStateFlow(LOADING)
    val config: StateFlow<SyncState> = _configState

    val overall: StateFlow<SyncState> = combine(
        circuits,
        constructors,
        drivers,
        races,
        config
    ) { circuits, constructors, drivers, races, config ->
        val all = listOf(circuits, constructors, drivers, races, config)
        if (all.all { it == DONE }) {
            contentSyncRepository.initialSyncCompleted = true
            return@combine DONE
        }
        if (all.all { it != LOADING }) {
            if (all.any { it == FAILED }) {
                return@combine FAILED
            }
        }
        return@combine LOADING
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = LOADING
    )

    fun startInitialSync() {
        startRemoteConfig()
        startSyncDrivers()
        startSyncConstructors()
        startSyncCircuits()
        startSyncRaces()
    }

    private fun startRemoteConfig() {

        if (!doesConfigRequireSyncUseCase()) {
            _configState.value = DONE
            return
        }

        _configState.value = LOADING
        viewModelScope.launch {
            resetConfigUseCase.ensureReset()

            val result = fetchConfigRepository.fetchAndApply()

            if (result) {
                _configState.value = DONE
            } else {
                _configState.value = FAILED
            }
        }
    }

    private fun startSyncDrivers() {
        if (_driversState.value != DONE) {
            _driversState.value = LOADING
            viewModelScope.launch {
                delay(DELAY_SYNC_MS)
                _driversState.value = when (driverRepository.populateDrivers()) {
                    Response.Successful -> DONE
                    else -> FAILED
                }
            }
        }
    }

    private fun startSyncConstructors() {
        if (_constructorsState.value != DONE) {
            _constructorsState.value = LOADING
            viewModelScope.launch {
                delay(DELAY_SYNC_MS)
                _constructorsState.value = when (constructorRepository.populateConstructors()) {
                    Response.Successful -> DONE
                    else -> FAILED
                }
            }
        }
    }

    private fun startSyncCircuits() {
        if (_circuitsState.value != DONE) {
            _circuitsState.value = LOADING
            viewModelScope.launch {
                delay(DELAY_SYNC_MS)
                _circuitsState.value = when (circuitRepository.populateCircuits()) {
                    Response.Successful -> DONE
                    else -> FAILED
                }
            }
        }
    }

    private fun startSyncRaces() {
        if (_racesState.value != DONE) {
            _racesState.value = LOADING
            viewModelScope.launch {
                delay(DELAY_SYNC_MS)
                _racesState.value = when (overviewRepository.populateOverview()) {
                    Response.Successful -> DONE
                    else -> FAILED
                }
                scheduleUpcomingNotificationsUseCase(false)
            }
        }
    }

    companion object {
        private val DELAY_SYNC_MS = 300L
    }
}
package tmg.flashback.feature.drivers.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.feature.drivers.presentation.stats.DriverStatBuilder.getOverallStats
import tmg.flashback.feature.drivers.presentation.stats.DriverStatBuilder.getSeasonStats
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.DriverHistorySeason
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class DriverStatsViewModel(
    private val driverRepository: DriverRepository,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val driver: MutableStateFlow<String?> = MutableStateFlow(null)
    private val selection: MutableStateFlow<DriverFilter?> = MutableStateFlow(null)

    private val driverFlow = driver
        .filterNotNull()
        .flatMapLatest { driver ->
            driverRepository.getDriverOverview(driver)
        }

    val uiState: StateFlow<DriverStatsUiState> = combine(
        driverFlow,
        selection.filterNotNull()
    ) { driver, selection ->
        when (selection) {
            DriverFilter.Overview -> {
                return@combine DriverStatsUiState(
                    driver = driver?.driver,
                    constructors = driver.getConstructors(),
                    availableSeasons = driver.availableSeasons,
                    selection = DriverFilter.Overview,
                    stats = driver.getOverallStats(),
                    data = driver.getOverallTeams()
                )
            }

            is DriverFilter.Season -> {
                val driverHistorySeason = driver?.standings
                    ?.firstOrNull { it.season == selection.season }

                return@combine DriverStatsUiState(
                    driver = driver?.driver,
                    constructors = (driverHistorySeason?.constructors ?: emptyList()).reversed(),
                    availableSeasons = driver.availableSeasons,
                    selection = DriverFilter.Season(selection.season),
                    stats = driverHistorySeason.getSeasonStats(),
                    data = driverHistorySeason.getSeasonRaces()
                )
            }
        }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, DriverStatsUiState())

    fun loadDriver(driverId: String, season: Int?) {
        driver.update { driverId }
        when (season) {
            null -> selection.update { DriverFilter.Overview }
            else -> selection.update { DriverFilter.Season(season) }
        }
        refresh()
    }

    fun changeSelection(season: DriverFilter) {
        selection.update { season }
    }

    fun refresh() {
        val driver = driver.value ?: return
        viewModelScope.launch(coroutineContext) {
            _loading.update { true }
            driverRepository.populateDriver(driver)
            _loading.update { false }
        }
    }

    private fun DriverHistory?.getOverallTeams(): DriverStatsData.Overview {
        return DriverStatsData.Overview(
            teams = (this?.standings ?: emptyList())
                .sortedByDescending { it.season }
                .associate { it.season to it.constructors }
        )
    }

    private fun DriverHistorySeason?.getSeasonRaces(): DriverStatsData.Season {
        return DriverStatsData.Season(
            races = this?.raceOverview ?: emptyList()
        )
    }

    private fun DriverHistory?.getConstructors(): List<Constructor> {
        return this?.standings
            ?.sortedByDescending { it.season }
            ?.flatMap { it.constructors }
            ?.distinct() ?: emptyList()
    }

    private val DriverHistory?.availableSeasons: List<Int>
        get() = (this?.standings ?: emptyList())
            .map { it.season }
            .sortedByDescending { it }
}

sealed interface DriverFilter {
    data object Overview : DriverFilter
    data class Season(val season: Int) : DriverFilter
}
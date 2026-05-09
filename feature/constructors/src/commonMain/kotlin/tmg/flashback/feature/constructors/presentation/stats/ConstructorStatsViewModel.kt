package tmg.flashback.feature.constructors.presentation.stats

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
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatBuilder.getOverallStats
import tmg.flashback.feature.constructors.presentation.stats.ConstructorStatBuilder.getSeasonStats
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.ConstructorHistorySeason
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ConstructorStatsViewModel(
    private val constructorRepository: ConstructorRepository,
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val constructor: MutableStateFlow<String?> = MutableStateFlow(null)
    private val selection: MutableStateFlow<ConstructorFilter?> = MutableStateFlow(null)

    private val constructorFlow = constructor
        .filterNotNull()
        .flatMapLatest { driver ->
            constructorRepository.getConstructorOverview(driver)
        }

    val uiState: StateFlow<ConstructorStatsUiState> = combine(
        constructorFlow,
        selection.filterNotNull()
    ) { constructor, selection ->
        when (selection) {
            ConstructorFilter.Overview -> {
                return@combine ConstructorStatsUiState(
                    constructor = constructor?.constructor,
                    availableSeasons = constructor.availableSeasons,
                    selection = ConstructorFilter.Overview,
                    stats = constructor.getOverallStats(),
                    data = constructor.getOverallTeams()
                )
            }

            is ConstructorFilter.Season -> {
                val driverHistorySeason = constructor?.standings
                    ?.firstOrNull { it.season == selection.season }

                return@combine ConstructorStatsUiState(
                    constructor = constructor?.constructor,
                    availableSeasons = constructor.availableSeasons,
                    selection = ConstructorFilter.Season(selection.season),
                    stats = driverHistorySeason.getSeasonStats(),
                    data = driverHistorySeason.getSeasonRaces()
                )
            }
        }
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, ConstructorStatsUiState())


    fun loadConstructor(constructorId: String, season: Int?) {
        constructor.update { constructorId }
        when (season) {
            null -> selection.update { ConstructorFilter.Overview }
            else -> selection.update { ConstructorFilter.Season(season) }
        }
        refresh()
    }

    fun changeSelection(season: ConstructorFilter) {
        selection.update { season }
    }

    fun refresh() {
        val constructor = constructor.value ?: return
        viewModelScope.launch(coroutineContext) {
            _loading.update { true }
            constructorRepository.populateConstructor(constructor)
            _loading.update { false }
        }
    }

    private fun ConstructorHistory?.getOverallTeams(): ConstructorStatsData.Overview {
        return ConstructorStatsData.Overview(
            items = (this?.standings ?: emptyList())
                .sortedByDescending { it.season }
                .map { model ->
                    ConstructorStatSeasonOverview(
                        season = model.season,
                        drivers = model.drivers.values
                            .sortedBy { it.championshipStanding }
                            .associate { it.championshipStanding to it.driver.driver },
                        standing = model.championshipStanding.takeIf { !model.isInProgress }
                    )
                }
        )
    }

    private fun ConstructorHistorySeason?.getSeasonRaces(): ConstructorStatsData.Season {
        return ConstructorStatsData.Season(
            drivers = this?.drivers?.values
                ?.toList()
                ?.sortedBy { it.championshipStanding }
                ?: emptyList()
        )
    }

    private val ConstructorHistory?.availableSeasons: List<Int>
        get() = (this?.standings ?: emptyList())
            .map { it.season }
            .sortedByDescending { it }
}


sealed interface ConstructorFilter {
    data object Overview : ConstructorFilter
    data class Season(val season: Int) : ConstructorFilter
}
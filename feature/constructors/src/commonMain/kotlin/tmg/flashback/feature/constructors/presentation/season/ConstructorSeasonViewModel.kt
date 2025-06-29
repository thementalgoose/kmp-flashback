package tmg.flashback.feature.constructors.presentation.season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import flashback.domain.formula1.generated.resources.ic_finishes_in_points
import flashback.domain.formula1.generated.resources.ic_podium
import flashback.domain.formula1.generated.resources.ic_qualifying_pole
import flashback.domain.formula1.generated.resources.ic_race_grid
import flashback.domain.formula1.generated.resources.ic_race_points
import flashback.domain.formula1.generated.resources.ic_standings
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_championship_standing
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_championship_standing_so_far
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points_finishes
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_qualifying_poles
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_podiums
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_wins
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_races
import flashback.presentation.ui.generated.resources.ic_menu_constructors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.ConstructorRepository
import tmg.flashback.formula1.model.ConstructorHistorySeason
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf

class ConstructorSeasonViewModel(
    private val constructorRepository: ConstructorRepository
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val seasonConstructor: MutableStateFlow<Pair<Int, String>?> = MutableStateFlow(null)

    val uiState: StateFlow<ConstructorSeasonUiState?> = seasonConstructor
        .filterNotNull()
        .flatMapLatest { (season, constructor) ->
            constructorRepository
                .getConstructorOverview(constructor)
                .map { season to it }
        }
        .map { (season, constructorHistory) ->

            if (constructorHistory == null) {
                return@map ConstructorSeasonUiState.NotFound
            }
            val seasonStanding = constructorHistory.standings.firstOrNull { it.season == season }
            if (seasonStanding == null) {
                return@map ConstructorSeasonUiState.NoRaces(season, constructorHistory.constructor)
            }

            return@map ConstructorSeasonUiState.Data(
                season = season,
                constructor = constructorHistory.constructor,
                isInProgress = seasonStanding.isInProgress,
                stats = getAllStats(seasonStanding),
                drivers = getAllDrivers(seasonStanding)
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun load(season: Int, driverId: String) {
        seasonConstructor.value = season to driverId
        refresh()
    }

    fun refresh() {
        val constructor = seasonConstructor.value?.second ?: return
        viewModelScope.launch {
            _loading.update { true }
            constructorRepository.populateConstructor(constructor)
            _loading.update { false }
        }
    }

    private fun getAllStats(history: ConstructorHistorySeason): List<ConstructorSeasonStat> {
        return buildList {
            if (history.isInProgress) {
                add(ConstructorSeasonStat(
                    icon = flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_constructors,
                    string = string.constructor_overview_stat_championship_standing_so_far,
                    value = history.championshipStanding?.ordinalAbbreviation ?: ""
                ))
            } else {
                add(ConstructorSeasonStat(
                    icon = flashback.presentation.ui.generated.resources.Res.drawable.ic_menu_constructors,
                    string = string.constructor_overview_stat_championship_standing,
                    value = history.championshipStanding?.ordinalAbbreviation ?: ""
                ))
            }

            add(ConstructorSeasonStat(
                icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_race_grid,
                string = string.constructor_overview_stat_races,
                value = history.races.toString()
            ))
            add(ConstructorSeasonStat(
                icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_standings,
                string = string.constructor_overview_stat_race_wins,
                value = history.wins.toString()
            ))
            add(ConstructorSeasonStat(
                icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_podium,
                string = string.constructor_overview_stat_race_podiums,
                value = history.podiums.toString()
            ))
            add(ConstructorSeasonStat(
                icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_race_points,
                string = string.constructor_overview_stat_points,
                value = history.points.roundToHalf()
            ))
            add(ConstructorSeasonStat(
                icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_finishes_in_points,
                string = string.constructor_overview_stat_points_finishes,
                value = history.finishInPoints.toString()
            ))
            add(ConstructorSeasonStat(
                icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_qualifying_pole,
                string = string.constructor_overview_stat_qualifying_poles,
                value = history.qualifyingPole.toString()
            ))
        }
    }

    private fun getAllDrivers(history: ConstructorHistorySeason): List<ConstructorSeasonDriver> {
        return history.drivers
            .map { it.value }
            .map { ConstructorSeasonDriver(it) }
    }
}
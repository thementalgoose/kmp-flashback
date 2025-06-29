package tmg.flashback.feature.drivers.presentation.season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.ic_best_finish
import flashback.domain.formula1.generated.resources.ic_championship_order
import flashback.domain.formula1.generated.resources.ic_finishes_in_points
import flashback.domain.formula1.generated.resources.ic_podium
import flashback.domain.formula1.generated.resources.ic_qualifying_front_row
import flashback.domain.formula1.generated.resources.ic_qualifying_pole
import flashback.domain.formula1.generated.resources.ic_qualifying_top_ten
import flashback.domain.formula1.generated.resources.ic_race_finishes
import flashback.domain.formula1.generated.resources.ic_race_points
import flashback.domain.formula1.generated.resources.ic_race_retirements
import flashback.domain.formula1.generated.resources.ic_race_starts
import flashback.domain.formula1.generated.resources.ic_standings
import flashback.domain.formula1.generated.resources.ic_star_filled_coloured
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_best_finish
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_championship_standing
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_championship_standing_so_far
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_podiums
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_points
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_points_finishes
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_qualifying_pole
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_qualifying_top_10
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_qualifying_top_3
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_wins
import flashback.presentation.localisation.generated.resources.driver_overview_stat_race_finishes
import flashback.presentation.localisation.generated.resources.driver_overview_stat_race_retirements
import flashback.presentation.localisation.generated.resources.driver_overview_stat_race_starts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tmg.flashback.data.repo.repository.DriverRepository
import tmg.flashback.formula1.model.DriverHistorySeason
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf

class DriverSeasonViewModel(
    private val driverRepository: DriverRepository
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val seasonDriver: MutableStateFlow<Pair<Int, String>?> = MutableStateFlow(null)

    val uiState: StateFlow<DriverSeasonUiState?> = seasonDriver
        .filterNotNull()
        .flatMapLatest { (season, driver) ->
            driverRepository
                .getDriverOverview(driver)
                .map { season to it }
        }
        .map { (season, driverHistory) ->

            if (driverHistory == null) {
                return@map DriverSeasonUiState.NotFound
            }
            val seasonStanding = driverHistory.standings.firstOrNull { it.season == season }
            if (seasonStanding == null) {
                return@map DriverSeasonUiState.NoRaces(season, driverHistory.driver)
            }

            return@map DriverSeasonUiState.Data(
                season = season,
                driver = driverHistory.driver,
                isInProgress = seasonStanding.isInProgress,
                stats = getAllStats(seasonStanding),
                races = getAllRaces(seasonStanding)
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun load(season: Int, driverId: String) {
        seasonDriver.value = season to driverId
        refresh()
    }

    fun refresh() {
        val driver = seasonDriver.value?.second ?: return
        viewModelScope.launch {
            _loading.update { true }
            driverRepository.populateDriver(driver)
            _loading.update { false }
        }
    }

    private fun getAllStats(overview: DriverHistorySeason): List<DriverSeasonStat> {
        return buildList {
            if (overview.isInProgress) {
                add(DriverSeasonStat(
                    string = string.driver_overview_stat_career_championship_standing_so_far,
                    icon = Res.drawable.ic_championship_order,
                    value = overview.championshipStanding?.ordinalAbbreviation ?: "N/A"
                ))
            } else {
                add(DriverSeasonStat(
                    string = string.driver_overview_stat_career_championship_standing,
                    icon = if (overview.championshipStanding == 1) Res.drawable.ic_star_filled_coloured else Res.drawable.ic_championship_order,
                    value = overview.championshipStanding?.ordinalAbbreviation ?: "N/A"
                ))
            }
            add(DriverSeasonStat(
                icon = Res.drawable.ic_standings,
                string = string.driver_overview_stat_career_wins,
                value = overview.wins.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_podium,
                string = string.driver_overview_stat_career_podiums,
                value = overview.podiums.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_race_starts,
                string = string.driver_overview_stat_race_starts,
                value = overview.raceStarts.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_race_finishes,
                string = string.driver_overview_stat_race_finishes,
                value = overview.raceFinishes.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_race_retirements,
                string = string.driver_overview_stat_race_retirements,
                value = overview.raceRetirements.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_best_finish,
                string = string.driver_overview_stat_career_best_finish,
                value = overview.bestFinish?.ordinalAbbreviation ?: "N/A"
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_finishes_in_points,
                string = string.driver_overview_stat_career_points_finishes,
                value = overview.finishesInPoints.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_race_points,
                string = string.driver_overview_stat_career_points,
                value = overview.points.roundToHalf()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_qualifying_pole,
                string = string.driver_overview_stat_career_qualifying_pole,
                value = overview.qualifyingPoles.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_qualifying_front_row,
                string = string.driver_overview_stat_career_qualifying_top_3,
                value = overview.qualifyingTop3.toString()
            ))
            add(DriverSeasonStat(
                icon = Res.drawable.ic_qualifying_top_ten,
                string = string.driver_overview_stat_career_qualifying_top_10,
                value = overview.totalQualifyingAbove(10).toString()
            ))
        }
    }

    private fun getAllRaces(overview: DriverHistorySeason): List<DriverSeasonRace> {
        return overview.raceOverview.map {
            DriverSeasonRace(result = it)
        }
    }
}
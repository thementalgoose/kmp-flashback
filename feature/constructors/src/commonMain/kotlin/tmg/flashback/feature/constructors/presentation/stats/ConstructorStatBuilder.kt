package tmg.flashback.feature.constructors.presentation.stats

import flashback.domain.formula1.generated.resources.Res.drawable
import flashback.domain.formula1.generated.resources.ic_championship_order
import flashback.domain.formula1.generated.resources.ic_finishes_in_points
import flashback.domain.formula1.generated.resources.ic_podium
import flashback.domain.formula1.generated.resources.ic_qualifying_pole
import flashback.domain.formula1.generated.resources.ic_race_grid
import flashback.domain.formula1.generated.resources.ic_race_points
import flashback.domain.formula1.generated.resources.ic_standings
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_best_championship_position
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_championship_standing
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_championship_standing_so_far
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_drivers_titles
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_points_finishes
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_qualifying_poles
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_podiums
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_race_wins
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_races
import flashback.presentation.localisation.generated.resources.constructor_overview_stat_titles
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_menu_constructors
import flashback.presentation.ui.generated.resources.ic_menu_drivers
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.ConstructorHistorySeason
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf

object ConstructorStatBuilder {
    fun ConstructorHistory?.getOverallStats(): List<ConstructorStat> {
        if (this == null) return emptyList()

        return buildList {
            add(ConstructorStat(
                icon = Res.drawable.ic_menu_constructors,
                string = string.constructor_overview_stat_titles,
                value = this@getOverallStats.championshipWins.toString()
            ))

            this@getOverallStats.bestChampionship?.let {
                add(ConstructorStat(
                    icon = drawable.ic_championship_order,
                    string = string.constructor_overview_stat_best_championship_position,
                    value = it.ordinalAbbreviation
                ))
            }

            add(ConstructorStat(
                icon = Res.drawable.ic_menu_drivers,
                string = string.constructor_overview_stat_drivers_titles,
                value = this@getOverallStats.driversChampionships.toString()
            ))

            add(ConstructorStat(
                icon = drawable.ic_race_grid,
                string = string.constructor_overview_stat_races,
                value = this@getOverallStats.races.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_standings,
                string = string.constructor_overview_stat_race_wins,
                value = this@getOverallStats.totalWins.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_podium,
                string = string.constructor_overview_stat_race_podiums,
                value = this@getOverallStats.totalPodiums.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_race_points,
                string = string.constructor_overview_stat_points,
                value = this@getOverallStats.totalPoints.roundToHalf()
            ))
            add(ConstructorStat(
                icon = drawable.ic_finishes_in_points,
                string = string.constructor_overview_stat_points_finishes,
                value = this@getOverallStats.finishesInPoints.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_qualifying_pole,
                string = string.constructor_overview_stat_qualifying_poles,
                value = this@getOverallStats.totalQualifyingPoles.toString()
            ))
        }
    }

    fun ConstructorHistorySeason?.getSeasonStats(): List<ConstructorStat> {
        if (this == null) return emptyList()

        return buildList {
            if (this@getSeasonStats.isInProgress) {
                add(ConstructorStat(
                    icon = Res.drawable.ic_menu_constructors,
                    string = string.constructor_overview_stat_championship_standing_so_far,
                    value = this@getSeasonStats.championshipStanding?.ordinalAbbreviation ?: ""
                ))
            } else {
                add(ConstructorStat(
                    icon = Res.drawable.ic_menu_constructors,
                    string = string.constructor_overview_stat_championship_standing,
                    value = this@getSeasonStats.championshipStanding?.ordinalAbbreviation ?: ""
                ))
            }

            add(ConstructorStat(
                icon = drawable.ic_race_grid,
                string = string.constructor_overview_stat_races,
                value = this@getSeasonStats.races.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_standings,
                string = string.constructor_overview_stat_race_wins,
                value = this@getSeasonStats.wins.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_podium,
                string = string.constructor_overview_stat_race_podiums,
                value = this@getSeasonStats.podiums.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_race_points,
                string = string.constructor_overview_stat_points,
                value = this@getSeasonStats.points.roundToHalf()
            ))
            add(ConstructorStat(
                icon = drawable.ic_finishes_in_points,
                string = string.constructor_overview_stat_points_finishes,
                value = this@getSeasonStats.finishInPoints.toString()
            ))
            add(ConstructorStat(
                icon = drawable.ic_qualifying_pole,
                string = string.constructor_overview_stat_qualifying_poles,
                value = this@getSeasonStats.qualifyingPole.toString()
            ))
        }
    }
}
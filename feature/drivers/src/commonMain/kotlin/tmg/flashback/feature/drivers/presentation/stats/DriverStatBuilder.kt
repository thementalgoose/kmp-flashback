package tmg.flashback.feature.drivers.presentation.stats

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.ic_best_finish
import flashback.domain.formula1.generated.resources.ic_championship_order
import flashback.domain.formula1.generated.resources.ic_driver
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
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_best_championship_position
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_best_finish
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_championship_standing
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_championship_standing_so_far
import flashback.presentation.localisation.generated.resources.driver_overview_stat_career_drivers_title
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
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.DriverHistorySeason
import tmg.flashback.infrastructure.extensions.ordinalAbbreviation
import tmg.flashback.infrastructure.extensions.roundToHalf

object DriverStatBuilder {

    fun DriverHistory?.getOverallStats(): List<DriverStat> {
        if (this == null) return emptyList()
        return buildList {
            add(DriverStat(
                icon = Res.drawable.ic_driver,
                string = string.driver_overview_stat_career_drivers_title,
                value = this@getOverallStats.championshipWins.toString(),
            ))
            if (this@getOverallStats.careerBestChampionship != null) {
                add(DriverStat(
                    icon = Res.drawable.ic_championship_order,
                    string = string.driver_overview_stat_career_best_championship_position,
                    value = this@getOverallStats.careerBestChampionship!!.ordinalAbbreviation
                ))
            }
            add(DriverStat(
                icon = Res.drawable.ic_standings,
                string = string.driver_overview_stat_career_wins,
                value = this@getOverallStats.careerWins.toString(),
            ))
            add(DriverStat(
                icon = Res.drawable.ic_podium,
                string = string.driver_overview_stat_career_podiums,
                value = this@getOverallStats.careerPodiums.toString(),
            ))
            add(DriverStat(
                icon = Res.drawable.ic_race_starts,
                string = string.driver_overview_stat_race_starts,
                value = this@getOverallStats.raceStarts.toString()
            ))
            add(DriverStat(
                icon = Res.drawable.ic_race_finishes,
                string = string.driver_overview_stat_race_finishes,
                value = this@getOverallStats.raceFinishes.toString()
            ))
            add(DriverStat(
                icon = Res.drawable.ic_race_retirements,
                string = string.driver_overview_stat_race_retirements,
                value = this@getOverallStats.raceRetirements.toString()
            ))
            add(DriverStat(
                icon = Res.drawable.ic_best_finish,
                string = string.driver_overview_stat_career_best_finish,
                value = this@getOverallStats.careerBestFinish.ordinalAbbreviation
            ))
            add(DriverStat(
                icon = Res.drawable.ic_finishes_in_points,
                string = string.driver_overview_stat_career_points_finishes,
                value = this@getOverallStats.careerFinishesInPoints.toString()
            ))
            add(DriverStat(
                icon = Res.drawable.ic_race_points,
                string = string.driver_overview_stat_career_points,
                value = this@getOverallStats.careerPoints.roundToHalf()
            ))
            add(DriverStat(
                icon = Res.drawable.ic_qualifying_pole,
                string = string.driver_overview_stat_career_qualifying_pole,
                value = this@getOverallStats.careerQualifyingPoles.toString(),
            ))
            add(DriverStat(
                icon = Res.drawable.ic_qualifying_front_row,
                string = string.driver_overview_stat_career_qualifying_top_3,
                value = this@getOverallStats.careerQualifyingTop3.toString()
            ))
            add(DriverStat(
                icon = Res.drawable.ic_qualifying_top_ten,
                string = string.driver_overview_stat_career_qualifying_top_10,
                value = this@getOverallStats.totalQualifyingAbove(10).toString()
            ))
        }
    }

    fun DriverHistorySeason?.getSeasonStats(): List<DriverStat> {
        if (this == null) return emptyList()

        return buildList {
            if (this@getSeasonStats.isInProgress) {
                add(
                    DriverStat(
                        string = string.driver_overview_stat_career_championship_standing_so_far,
                        icon = Res.drawable.ic_championship_order,
                        value = this@getSeasonStats.championshipStanding?.ordinalAbbreviation
                            ?: "N/A"
                    )
                )
            } else {
                add(
                    DriverStat(
                        string = string.driver_overview_stat_career_championship_standing,
                        icon = if (this@getSeasonStats.championshipStanding == 1) Res.drawable.ic_star_filled_coloured else Res.drawable.ic_championship_order,
                        value = this@getSeasonStats.championshipStanding?.ordinalAbbreviation
                            ?: "N/A"
                    )
                )
            }
            add(
                DriverStat(
                    icon = Res.drawable.ic_standings,
                    string = string.driver_overview_stat_career_wins,
                    value = this@getSeasonStats.wins.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_podium,
                    string = string.driver_overview_stat_career_podiums,
                    value = this@getSeasonStats.podiums.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_race_starts,
                    string = string.driver_overview_stat_race_starts,
                    value = this@getSeasonStats.raceStarts.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_race_finishes,
                    string = string.driver_overview_stat_race_finishes,
                    value = this@getSeasonStats.raceFinishes.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_race_retirements,
                    string = string.driver_overview_stat_race_retirements,
                    value = this@getSeasonStats.raceRetirements.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_best_finish,
                    string = string.driver_overview_stat_career_best_finish,
                    value = this@getSeasonStats.bestFinish?.ordinalAbbreviation ?: "N/A"
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_finishes_in_points,
                    string = string.driver_overview_stat_career_points_finishes,
                    value = this@getSeasonStats.finishesInPoints.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_race_points,
                    string = string.driver_overview_stat_career_points,
                    value = this@getSeasonStats.points.roundToHalf()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_qualifying_pole,
                    string = string.driver_overview_stat_career_qualifying_pole,
                    value = this@getSeasonStats.qualifyingPoles.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_qualifying_front_row,
                    string = string.driver_overview_stat_career_qualifying_top_3,
                    value = this@getSeasonStats.qualifyingTop3.toString()
                )
            )
            add(
                DriverStat(
                    icon = Res.drawable.ic_qualifying_top_ten,
                    string = string.driver_overview_stat_career_qualifying_top_10,
                    value = this@getSeasonStats.totalQualifyingAbove(10).toString()
                )
            )
        }
    }
}
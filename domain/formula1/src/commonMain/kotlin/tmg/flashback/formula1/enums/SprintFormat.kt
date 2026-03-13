package tmg.flashback.formula1.enums

import tmg.flashback.formula1.constants.Formula1

enum class SprintFormat {
    /**
     * Friday: Qualifying. Sets grid for sprint
     * Saturday: Sprint. Sets grid for race
     * Sunday: Race
     */
    FORMAT_2021_2022,
    /**
     * Friday: Qualifying. Sets grid for race
     * Saturday: Sprint Qualifying. Sets grid for sprint race
     * Saturday: Sprint Race
     * Sunday: Race
     */
    FORMAT_2023,
    /**
     * Friday: Spring Qualifying. Sets grid for sprint
     * Saturday: Sprint Race.
     * Saturday: Qualifying. Sets grid for race
     * Sunday: Race
     */
    FORMAT_CURRENT;

    companion object {
        fun getSeasonFormat(season: Int): SprintFormat? {
            return when {
                season < Formula1.sprintsIntroducedIn -> null
                season in listOf(2021, 2022) -> FORMAT_2021_2022
                season in listOf(2023) -> FORMAT_2023
                else -> FORMAT_CURRENT
            }
        }
    }
}
package tmg.flashback.feature.weekend.utils

import tmg.flashback.feature.weekend.presentation.WeekendTabs

internal fun getWeekendEventOrder(
    isSprint: Boolean,
    season: Int
): List<WeekendTabs> {
    when {
        season < 2021 -> {
            return listOf(
                WeekendTabs.Qualifying,
                WeekendTabs.Race
            )
        }
        season in 2021..2022 -> {
            return listOfNotNull(
                WeekendTabs.Qualifying,
                WeekendTabs.SprintRace.takeIf { isSprint },
                WeekendTabs.Race,
            )
        }
        season == 2023 -> {
            return listOfNotNull(
                WeekendTabs.Qualifying,
                WeekendTabs.SprintQualifying.takeIf { isSprint },
                WeekendTabs.SprintRace.takeIf { isSprint },
                WeekendTabs.Race,
            )
        }
        else -> {
            return listOfNotNull(
                WeekendTabs.SprintQualifying.takeIf { isSprint },
                WeekendTabs.SprintRace.takeIf { isSprint },
                WeekendTabs.Qualifying,
                WeekendTabs.Race,
            )
        }
    }
}
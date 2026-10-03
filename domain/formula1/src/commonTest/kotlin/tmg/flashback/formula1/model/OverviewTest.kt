package tmg.flashback.formula1.model

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import tmg.flashback.infrastructure.datetime.now
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OverviewTest {

    @Test
    fun `SeasonOverview completed returns correct number of rounds`() {

        val model = Overview(season = 2020, overviewRaces = listOf(
            OverviewRace.model(
                raceName = "day before",
                date = LocalDate.now().minus(DatePeriod(0, 0, 1))
            ),
            OverviewRace.model(
                raceName = "today",
                date = LocalDate.now()
            ),
            OverviewRace.model(
                raceName = "day after",
                date = LocalDate.now().plus(DatePeriod(0, 0, 1))
            )
        ))

        assertEquals(1, model.completed)
    }

    @Test
    fun `SeasonOverview upcoming returns correct number of rounds`() {

        val model = Overview(season = 2020, overviewRaces = listOf(
            OverviewRace.model(
                raceName = "day before",
                date = LocalDate.now().minus(DatePeriod(0, 0, 1))
            ),
            OverviewRace.model(
                raceName = "today",
                date = LocalDate.now()
            ),
            OverviewRace.model(
                raceName = "day after",
                date = LocalDate.now().plus(DatePeriod(0, 0, 1))
            )
        ))

        assertEquals(2, model.upcoming)
    }

    @Test
    fun `SeasonOverview scheduledToday returns correct number of rounds`() {

        val model = Overview(season = 2020, overviewRaces = listOf(
            OverviewRace.model(
                raceName = "day before",
                date = LocalDate.now().minus(DatePeriod(0, 0, 1))
            ),
            OverviewRace.model(
                raceName = "today",
                date = LocalDate.now()
            ),
            OverviewRace.model(
                raceName = "day after",
                date = LocalDate.now().plus(DatePeriod(0, 0, 1))
            )
        ))

        assertEquals(1, model.scheduledToday)
    }
}
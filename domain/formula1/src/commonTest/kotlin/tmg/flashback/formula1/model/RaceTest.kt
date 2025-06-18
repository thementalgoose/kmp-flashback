package tmg.flashback.formula1.model

import tmg.flashback.formula1.model.QualifyingType.Q1
import tmg.flashback.formula1.model.QualifyingType.Q2
import tmg.flashback.formula1.model.QualifyingType.Q3
import tmg.flashback.formula1.model.SprintQualifyingType.SQ1
import tmg.flashback.formula1.model.SprintQualifyingType.SQ2
import tmg.flashback.formula1.model.SprintQualifyingType.SQ3
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class RaceTest {

    @Test
    fun `drivers and constructor initialises to list of all info and qualifying data`() {
        val constructor1 = Constructor.model(id = "constructor1")
        val constructor2 = Constructor.model(id = "constructor2")
        val driver1 = DriverEntry.model(driver = Driver.model(id = "driver1"), constructor = constructor1)
        val driver2 = DriverEntry.model(driver = Driver.model(id = "driver2"), constructor = constructor1)
        val driver3 = DriverEntry.model(driver = Driver.model(id = "driver2"), constructor = constructor2)
        val driver4 = DriverEntry.model(driver = Driver.model(id = "driver3"), constructor = constructor2)

        val model = Race.model(
            qualifying = listOf(QualifyingRound.model(results = listOf(
                QualifyingResult.model(
                    driver = driver1
                ),
                QualifyingResult.model(
                    driver = driver2
                ),
                QualifyingResult.model(
                    driver = driver1
                ),
                QualifyingResult.model(
                    driver = driver3
                )
            ))),
            race = listOf(RaceResult.model(
                driver = driver4
            )),
            sprint = SprintResult(emptyList(), emptyList())
        )

        assertEquals(listOf(constructor1, constructor2), model.constructors)
        assertEquals(listOf(driver1, driver2, driver3, driver4), model.entries)
    }

    private data class TestCase(
        val qualifyingTypes: List<QualifyingType>,
        val query: QualifyingType,
        val expectedResult: Boolean
    )
    private val testCases = listOf(
        TestCase(listOf(Q1,Q2,Q3),Q1,true),
        TestCase(listOf(Q1,Q2,Q3),Q2,true),
        TestCase(listOf(Q1,Q2,Q3),Q3,true),
        TestCase(listOf(Q1,Q2,Q3),Q1,true),
        TestCase(listOf(Q1,Q2,Q3),Q2,true),
        TestCase(listOf(Q1,Q2,Q3),Q3,true),
        TestCase(listOf(Q1),Q1,true),
        TestCase(listOf(Q1),Q2,false),
        TestCase(listOf(Q1),Q3,false),
    )

    @Test
    fun `has qualifying type with data loaded returns true`() {
        testCases.forEach { (inputQualifying, query, expectedResult) ->
            val model = Race.model(qualifying = inputQualifying.map { qualiType ->
                QualifyingRound.model(label = qualiType)
            })

            assertEquals(expectedResult, model.has(query))
        }
    }

    @Test
    fun `has data returns true if qualifying is not empty`() {
        val model = Race.model(
            qualifying = listOf(QualifyingRound.model()),
            race = emptyList()
        )

        assertEquals(true, model.hasData)
    }

    @Test
    fun `has data returns true if race is not empty`() {
        val model = Race.model(
            qualifying = emptyList(),
            race = listOf(RaceResult.model())
        )

        assertEquals(true, model.hasData)
    }

    @Test
    fun `has data returns true if qualifying and race is not empty`() {
        val model = Race.model(
            qualifying = listOf(QualifyingRound.model()),
            race = listOf(RaceResult.model())
        )

        assertEquals(true, model.hasData)
    }

    @Test
    fun `has data returns false if qualifying and race are empty`() {
        val model = Race.model(
            qualifying = emptyList(),
            race = emptyList()
        )

        assertEquals(false, model.hasData)
    }

    @Test
    fun `q1 fastest lap`() {
        val fastest = LapTime.model(hours = 1, mins = 2, seconds = 3, milliseconds = 101)

        val driver1 = Driver.model(id = "1")
        val driver2 = Driver.model(id = "2")
        val model = Race.model(qualifying = listOf(
            QualifyingRound(
                Q1, 1, listOf(
                QualifyingResult.model(
                    driver = DriverEntry.model(driver = driver1),
                    lapTime = fastest
                ),
                QualifyingResult.model(
                    driver = DriverEntry.model(driver = driver2),
                    lapTime = LapTime.model(hours = 1, mins = 2, seconds = 3, milliseconds = 102)
                )
            ))
        ))

        assertEquals(fastest, model.q1FastestLap)
    }

    @Test
    fun `q2 fastest lap`() {
        val fastest = LapTime.model(hours = 1, mins = 2, seconds = 3, milliseconds = 13)

        val driver1 = Driver.model(id = "1")
        val driver2 = Driver.model(id = "2")
        val model = Race.model(qualifying = listOf(
            QualifyingRound(
                Q2, 1, listOf(
                QualifyingResult.model(
                    driver = DriverEntry.model(driver = driver1),
                    lapTime = fastest
                ),
                QualifyingResult.model(
                    driver = DriverEntry.model(driver = driver2),
                    lapTime = LapTime.model(hours = 1, mins = 2, seconds = 3, milliseconds = 102)
                )
            ))
        ))

        assertEquals(fastest, model.q2FastestLap)
    }

    @Test
    fun `q3 fastest lap`() {
        val fastest = LapTime.model(hours = 1, mins = 2, seconds = 3, milliseconds = 101)

        val driver1 = Driver.model(id = "1")
        val driver2 = Driver.model(id = "2")
        val model = Race.model(qualifying = listOf(
            QualifyingRound(Q3, 1, listOf(
                QualifyingResult.model(
                    driver = DriverEntry.model(driver = driver1),
                    lapTime = fastest
                ),
                QualifyingResult.model(
                    driver = DriverEntry.model(driver = driver2),
                    lapTime = LapTime.model(hours = 1, mins = 2, seconds = 3, milliseconds = 102)
                )
            ))
        ))

        assertEquals(fastest, model.q3FastestLap)
    }

    @Test
    fun `driver overview with all driver values`() {
        val driver = Driver.model(id = "mDriver")
        val q1 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 2
        )
        val q2 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 3
        )
        val q3 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 4
        )
        val race = RaceResult.model(
            driver = DriverEntry.model(driver = driver)
        )
        val sprintQ1 = SprintQualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 2
        )
        val sprintQ2 = SprintQualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 3
        )
        val sprintQ3 = SprintQualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 4
        )
        val sprintRace = SprintRaceResult.model(
            driver = DriverEntry.model(driver = driver),
        )

        val model = Race.model(
            qualifying = listOf(
                QualifyingRound.model(Q1, 1, listOf(q1)),
                QualifyingRound.model(Q2, 2, listOf(q2)),
                QualifyingRound.model(Q3, 3, listOf(q3)),
            ),
            sprint = SprintResult.model(
                qualifying = listOf(
                    SprintQualifyingRound.model(SQ1, 1, listOf(sprintQ1)),
                    SprintQualifyingRound.model(SQ2, 2, listOf(sprintQ2)),
                    SprintQualifyingRound.model(SQ3, 3, listOf(sprintQ3)),
                ),
                race = listOf(sprintRace)
            ),
            race = listOf(
                race
            )
        )

        val expectedDriverOverview = RaceDriverOverview.model(
            driver = DriverEntry.model(driver = driver),
            q1 = q1,
            q2 = q2,
            q3 = q3,
            sprintQ1 = sprintQ1,
            sprintQ2 = sprintQ2,
            sprintQ3 = sprintQ3,
            sprintRace = sprintRace,
            race = race
        )

        assertEquals(expectedDriverOverview, model.driverOverview(driver.id))
    }

    @Test
    fun `has sprint values return false if no sprint data available`() {
        val race = Race.model(
            sprint = SprintResult.model(
                qualifying = emptyList(),
                race = emptyList()
            )
        )

        assertFalse(race.hasSprint)
        assertFalse(race.hasSprintQualifying)
        assertFalse(race.hasSprintRace)
    }

    @Test
    fun `has sprint qualifying values return true`() {
        val race = Race.model(
            sprint = SprintResult.model(
                qualifying = listOf(SprintQualifyingRound.model()),
                race = emptyList()
            )
        )

        assertTrue(race.hasSprint)
        assertTrue(race.hasSprintQualifying)
        assertFalse(race.hasSprintRace)
    }

    @Test
    fun `has sprint race values return true`() {
        val race = Race.model(
            sprint = SprintResult.model(
                qualifying = emptyList(),
                race = listOf(SprintRaceResult.model())
            )
        )

        assertTrue(race.hasSprint)
        assertFalse(race.hasSprintQualifying)
        assertTrue(race.hasSprintRace)
    }

    @Test
    fun `driver overview with unknown driver id returns null`() {
        val driver = Driver.model(id = "mDriver")
        val q1 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 2
        )
        val q2 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 3
        )
        val q3 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 4
        )
        val qSprint = SprintRaceResult.model(
            driver = DriverEntry.model(driver = driver),
        )
        val race = RaceResult.model(
            driver = DriverEntry.model(driver = driver)
        )
        val sprintQ1 = SprintQualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 2
        )
        val sprintQ2 = SprintQualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 3
        )
        val sprintQ3 = SprintQualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 4
        )
        val sprintRace = SprintRaceResult.model(
            driver = DriverEntry.model(driver = driver),
        )

        val model = Race.model(
            qualifying = listOf(
                QualifyingRound.model(Q1, 1, listOf(q1)),
                QualifyingRound.model(Q2, 2, listOf(q2)),
                QualifyingRound.model(Q3, 3, listOf(q3)),
            ),
            sprint = SprintResult.model(
                qualifying = listOf(
                    SprintQualifyingRound.model(SQ1, 1, listOf(sprintQ1)),
                    SprintQualifyingRound.model(SQ2, 2, listOf(sprintQ2)),
                    SprintQualifyingRound.model(SQ3, 3, listOf(sprintQ3)),
                ),
                race = listOf(sprintRace)
            ),
            race = listOf(
                race
            )
        )

        assertNull(model.driverOverview("UNKNOWN"))
    }

    @Test
    fun `driver overview with all no sprint driver`() {
        val driver = Driver.model(id = "mDriver")
        val q1 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 2
        )
        val q2 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 3
        )
        val q3 = QualifyingResult.model(
            driver = DriverEntry.model(driver = driver),
            position = 4
        )
        val qSprint = SprintRaceResult.model(
            driver = DriverEntry.model(driver = Driver.model()),
        )
        val race = RaceResult.model(
            driver = DriverEntry.model(driver = driver)
        )

        val model = Race.model(
            qualifying = listOf(
                QualifyingRound.model(Q1, 1, listOf(q1)),
                QualifyingRound.model(Q2, 2, listOf(q2)),
                QualifyingRound.model(Q3, 3, listOf(q3))
            ),
            sprint = SprintResult.model(
                qualifying = emptyList(),
                race = emptyList()
            ),
            race = listOf(
                race
            )
        )

        val expectedDriverOverview = RaceDriverOverview.model(
            driver = DriverEntry.model(driver = driver),
            q1 = q1,
            q2 = q2,
            q3 = q3,
            sprintQ1 = null,
            sprintQ2 = null,
            sprintQ3 = null,
            sprintRace = null,
            race = race
        )

        assertEquals(expectedDriverOverview, model.driverOverview(driver.id))
    }

    @Test
    fun `constructor standings combines points based`() {

        val constructor1 = Constructor.model(id = "1")
        val constructor2 = Constructor.model(id = "2")

        val model = Race.model(
            qualifying = emptyList(),
            race = listOf(
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor1),
                    points = 2.0
                ),
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor2),
                    points = 4.0
                ),
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor1),
                    points = 3.0
                ),
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor2),
                    points = 9.0
                )
            ),
            sprint = SprintResult(emptyList(), emptyList())
        )
        val expected = listOf(
            RaceConstructorStandings.model(points = 13.0, constructor = constructor2),
            RaceConstructorStandings.model(points = 5.0, constructor = constructor1)
        )

        assertEquals(expected, model.constructorStandings)
    }

    @Test
    fun `constructor standings combines points based including sprint qualifying`() {

        val constructor1 = Constructor.model(id = "1")
        val constructor2 = Constructor.model(id = "2")

        val model = Race.model(
            qualifying = emptyList(),
            sprint = SprintResult.model(
                race = listOf(
                    SprintRaceResult.model(
                        driver = DriverEntry.model(constructor = constructor1),
                        points = 2.0
                    )
                ),
                qualifying = emptyList()
            ),
            race = listOf(
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor1),
                    points = 2.0
                ),
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor2),
                    points = 4.0
                ),
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor1),
                    points = 3.0
                ),
                RaceResult.model(
                    driver = DriverEntry.model(constructor = constructor2),
                    points = 9.0
                )
            )
        )
        val expected = listOf(
            RaceConstructorStandings.model(points = 13.0, constructor = constructor2),
            RaceConstructorStandings.model(points = 5.0 + 2.0, constructor = constructor1)
        )

        assertEquals(expected, model.constructorStandings)
    }
}
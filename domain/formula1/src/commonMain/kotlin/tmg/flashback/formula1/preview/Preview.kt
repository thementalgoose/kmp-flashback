package tmg.flashback.formula1.preview

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import tmg.flashback.formula1.enums.RaceStatus
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.CircuitHistoryRace
import tmg.flashback.formula1.model.CircuitHistoryRaceResult
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverEntry
import tmg.flashback.formula1.model.FastestLap
import tmg.flashback.formula1.model.LapTime
import tmg.flashback.formula1.model.Location
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.RaceResult
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.SprintRaceResult
import tmg.flashback.infrastructure.datetime.now

fun Circuit.Companion.preview(
    id: String = "circuitId"
) = Circuit(
    id = id,
    name = "Silverstone",
    wikiUrl = "https://www.google.com",
    city = "Towcester",
    country = "United Kingdom",
    countryISO = "GBR",
    location = Location(-51.1, 1.1)
)

fun Driver.Companion.preview(
    id: String = "driverId"
) = Driver(
    id = id,
    firstName = "Sonny",
    lastName = "Hayes",
    code = "SMI",
    number = 12,
    wikiUrl = "wikiUrl",
    photoUrl = "https://i.imgur.com/ftD4H0D.png",
    dateOfBirth = LocalDate(1990, 1, 1),
    nationality = "British",
    nationalityISO = "GBR"
)

fun Constructor.Companion.preview() = Constructor(
    id = "apex",
    name = "Apex GP",
    wikiUrl = "wikiUrl",
    photoUrl = "https://i.imgur.com/ftD4H0D.png",
    nationality = "British",
    nationalityISO = "GBR",
    color = 0xFFC9B68C.toInt()
)

fun DriverEntry.Companion.preview(
    driverId: String = "driverId"
) = DriverEntry(
    driver = Driver.preview(driverId),
    constructor = Constructor.preview()
)

fun Circuit.Companion.preview() = Circuit(
    id = "circuitId",
    name = "Silverstone",
    wikiUrl = "wikiUrl",
    city = "Towcester",
    country = "United Kingdom",
    countryISO = "GBR",
    location = Location(52.073618, -1.022214)
)

fun CircuitHistoryRace.Companion.preview() = CircuitHistoryRace(
    name = "British Grand Prix",
    season = 2020,
    round = 1,
    wikiUrl = null,
    date = LocalDate(2020, 1, 1),
    time = LocalTime(12, 0),
    preview = listOf(
        CircuitHistoryRaceResult.preview(1),
        CircuitHistoryRaceResult.preview(2),
        CircuitHistoryRaceResult.preview(3)
    )
)

fun CircuitHistoryRaceResult.Companion.preview(
    position: Int = 1,
) = CircuitHistoryRaceResult(
    position = position,
    driver = Driver.preview(),
    constructor = Constructor.preview()
)

fun RaceInfo.Companion.preview() = RaceInfo(
    season = 2020,
    round = 1,
    date = LocalDate(2020, 1, 1),
    time = LocalTime(12, 0),
    name = "British Grand Prix",
    laps = "100",
    youtube = "youtubeUrl",
    wikipediaUrl = "wikipediaUrl",
    circuit = Circuit.preview()
)

fun LapTime.Companion.preview() = LapTime(0, 1, 2, 123)

fun RaceResult.Companion.preview(
    driverId: String = "driverId"
) = RaceResult(
    entry = DriverEntry.preview(
        driverId = driverId
    ),
    time = LapTime.preview(),
    points = 25.0,
    grid = 1,
    qualified = 1,
    finish = 1,
    status = RaceStatus.FINISHED,
    fastestLap = FastestLap(1, LapTime.preview())
)

fun SprintRaceResult.Companion.preview() = SprintRaceResult(
    entry = DriverEntry.preview(),
    time = LapTime.preview(),
    points = 25.0,
    grid = 1,
    finish = 1,
    status = RaceStatus.FINISHED,
)

fun Schedule.Companion.preview(label: String) = Schedule(
    label = label,
    date = LocalDate.now(),
    time = LocalTime.now(),
    weather = null
)
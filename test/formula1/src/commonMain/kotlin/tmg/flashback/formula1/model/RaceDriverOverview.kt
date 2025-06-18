package tmg.flashback.formula1.model

fun RaceDriverOverview.Companion.model(
    driver: DriverEntry = DriverEntry.model(),
    q1: QualifyingResult? = QualifyingResult.model(),
    q2: QualifyingResult? = QualifyingResult.model(),
    q3: QualifyingResult? = QualifyingResult.model(),
    race: RaceResult? = RaceResult.model(),
    sprintQ1: SprintQualifyingResult? = SprintQualifyingResult.model(),
    sprintQ2: SprintQualifyingResult? = SprintQualifyingResult.model(),
    sprintQ3: SprintQualifyingResult? = SprintQualifyingResult.model(),
    sprintRace: SprintRaceResult? = SprintRaceResult.model(),
): RaceDriverOverview = RaceDriverOverview(
    entry = driver,
    q1 = q1,
    q2 = q2,
    q3 = q3,
    race = race,
    sprintQ1 = sprintQ1,
    sprintQ2 = sprintQ2,
    sprintQ3 = sprintQ3,
    sprintRace = sprintRace
)
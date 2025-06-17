package tmg.flashback.formula1.model

data class RaceDriverOverview(
    val entry: DriverEntry,
    val q1: QualifyingResult?,
    val q2: QualifyingResult?,
    val q3: QualifyingResult?,
    val sprintQ1: SprintQualifyingResult?,
    val sprintQ2: SprintQualifyingResult?,
    val sprintQ3: SprintQualifyingResult?,
    val sprintRace: SprintRaceResult?,
    val race: RaceResult?,
    val qualified: Int? = q3?.position ?: q2?.position ?: q1?.position,
    val officialQualifyingPosition: Int? = race?.qualified ?: qualified
) {
    companion object
}
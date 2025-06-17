package tmg.flashback.formula1.model

data class SprintResult(
    val qualifying: List<SprintQualifyingRound>,
    val race: List<SprintRaceResult>
) {
    companion object
}
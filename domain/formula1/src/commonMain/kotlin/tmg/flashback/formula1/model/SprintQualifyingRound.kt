package tmg.flashback.formula1.model


data class SprintQualifyingRound(
    val label: SprintQualifyingType,
    val order: Int,
    val results: List<SprintQualifyingResult>
) {
    companion object
}
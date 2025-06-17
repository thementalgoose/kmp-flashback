package tmg.flashback.formula1.model

data class QualifyingRound(
    val label: QualifyingType,
    val order: Int,
    val results: List<QualifyingResult>
) {
    companion object
}
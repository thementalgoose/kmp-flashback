package tmg.flashback.formula1.model

fun SprintQualifyingRound.Companion.model(
    label: SprintQualifyingType = SprintQualifyingType.SQ1,
    order: Int = 1,
    results: List<SprintQualifyingResult> = listOf(
        SprintQualifyingResult.model()
    )
): SprintQualifyingRound = SprintQualifyingRound(
    label = label,
    order = order,
    results = results
)
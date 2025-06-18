package tmg.flashback.formula1.model

fun QualifyingRound.Companion.model(
    label: QualifyingType = QualifyingType.Q1,
    order: Int = 1,
    results: List<QualifyingResult> = listOf(
        QualifyingResult.model()
    )
): QualifyingRound = QualifyingRound(
    label = label,
    order = order,
    results = results
)
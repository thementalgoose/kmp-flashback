package tmg.flashback.formula1.model

fun CircuitHistory.Companion.model(
    data: Circuit = Circuit.model(),
    results: List<CircuitHistoryRace> = listOf(
        CircuitHistoryRace.model()
    )
): CircuitHistory = CircuitHistory(
    data = data,
    results = results
)
package tmg.flashback.persistence.flashback.models.circuit

fun CircuitRound.Companion.model(
    circuitId: String = "circuitId",
    season: Int = 2020,
    round: Int = 1,
    name: String = "name",
    date: String = "1995-10-12",
    time: String? = "12:34",
    wikiUrl: String? = "wikiUrl"
): CircuitRound = CircuitRound(
    circuitId = circuitId,
    season = season,
    round = round,
    name = name,
    date = date,
    time = time,
    wikiUrl = wikiUrl,
)
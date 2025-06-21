package tmg.flashback.flashbackapi.api.models.races

fun SprintEvent.Companion.model(
    qualifying: Map<String, SprintQualifyingResult>? = mapOf(
        "driverId" to SprintQualifyingResult.model()
    ),
    race: Map<String, SprintRaceResult>? = mapOf(
        "driverId" to SprintRaceResult.model()
    )
): SprintEvent = SprintEvent(
    qualifying = qualifying,
    race = race
)
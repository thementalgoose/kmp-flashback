package tmg.flashback.flashbackapi.api.models.constructors

fun ConstructorHistory.Companion.model(
    constructor: Constructor = Constructor.model(),
    standings: Map<String, ConstructorHistoryStanding> = mapOf(

    )
): ConstructorHistory = ConstructorHistory(
    constructor = constructor,
    standings = standings
)
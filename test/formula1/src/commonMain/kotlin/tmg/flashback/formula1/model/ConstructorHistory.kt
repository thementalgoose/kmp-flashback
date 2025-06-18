package tmg.flashback.formula1.model

fun ConstructorHistory.Companion.model(
    constructor: Constructor = Constructor.model(),
    standings: List<ConstructorHistorySeason> = listOf(
        ConstructorHistorySeason.model()
    )
): ConstructorHistory = ConstructorHistory(
    constructor = constructor,
    standings = standings
)
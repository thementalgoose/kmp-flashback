package tmg.flashback.formula1.model

fun LineupOverview.Companion.model(
    constructor: Constructor = Constructor.model(),
    drivers: Map<Driver, List<Int>> = mapOf(Driver.model() to listOf(2020))
): LineupOverview = LineupOverview(
    constructor = constructor,
    drivers = drivers
)
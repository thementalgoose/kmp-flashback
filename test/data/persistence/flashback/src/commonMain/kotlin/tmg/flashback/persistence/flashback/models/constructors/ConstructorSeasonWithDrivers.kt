package tmg.flashback.persistence.flashback.models.constructors

fun ConstructorSeasonWithDrivers.Companion.model(
    constructorSeason: ConstructorSeason = ConstructorSeason.model(),
    drivers: List<ConstructorSeasonDriverWithDriver> = listOf(
        ConstructorSeasonDriverWithDriver.model()
    )
): ConstructorSeasonWithDrivers = ConstructorSeasonWithDrivers(
    constructorSeason = constructorSeason,
    drivers = drivers
)
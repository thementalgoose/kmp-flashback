package tmg.flashback.formula1.enums

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import tmg.flashback.formula1.enums.Tyre.BLUE_DRY_13
import tmg.flashback.formula1.enums.Tyre.BLUE_WET_13
import tmg.flashback.formula1.enums.Tyre.BLUE_WET_18
import tmg.flashback.formula1.enums.Tyre.GRAY_DRY_13
import tmg.flashback.formula1.enums.Tyre.GREEN_WET_13
import tmg.flashback.formula1.enums.Tyre.GREEN_WET_18
import tmg.flashback.formula1.enums.Tyre.ORANGE_DRY_13
import tmg.flashback.formula1.enums.Tyre.ORANGE_WET_13
import tmg.flashback.formula1.enums.Tyre.PINK_DRY_13
import tmg.flashback.formula1.enums.Tyre.PURPLE_DRY_13
import tmg.flashback.formula1.enums.Tyre.RED_DRY_13
import tmg.flashback.formula1.enums.Tyre.RED_DRY_18
import tmg.flashback.formula1.enums.Tyre.WHITE_DRY_13
import tmg.flashback.formula1.enums.Tyre.WHITE_DRY_18
import tmg.flashback.formula1.enums.Tyre.YELLOW_DRY_13
import tmg.flashback.formula1.enums.Tyre.YELLOW_DRY_18

fun SeasonTyres.Companion.getBySeason(season: Int): SeasonTyres? {
    val result = TyreInfo.entries.firstOrNull { it.includesYear(season) } ?: return null
    return SeasonTyres(
        season = season,
        tyres = result.tyres
    )
}

fun SeasonTyres.Companion.hasEntryForSeason(season: Int): Boolean {
    return TyreInfo.entries.any { it.includesYear(season) }
}

data class SeasonTyres(
    val season: Int,
    val tyres: List<TyreLabel>
) {
    companion object
}

private enum class TyreInfo(
    val from: Int,
    val to: Int? = null,
    val tyres: List<TyreLabel>
) {
    S2011(
        singleYear = 2011,
        tyres = listOf(
            TyreLabel(tyre = RED_DRY_13, label = string.tyre_super_soft),
            TyreLabel(tyre = YELLOW_DRY_13, label = string.tyre_soft),
            TyreLabel(tyre = WHITE_DRY_13, label = string.tyre_medium),
            TyreLabel(tyre = GRAY_DRY_13, label = string.tyre_hard),
            TyreLabel(tyre = BLUE_WET_13, label = string.tyre_intermediate),
            TyreLabel(tyre = ORANGE_WET_13, label = string.tyre_wet)
        )
    ),
    S2012(
        singleYear = 2012,
        tyres = listOf(
            TyreLabel(tyre = RED_DRY_13, label = string.tyre_super_soft),
            TyreLabel(tyre = YELLOW_DRY_13, label = string.tyre_soft),
            TyreLabel(tyre = WHITE_DRY_13, label = string.tyre_medium),
            TyreLabel(tyre = GRAY_DRY_13, label = string.tyre_hard),
            TyreLabel(tyre = GREEN_WET_13, label = string.tyre_intermediate),
            TyreLabel(tyre = BLUE_WET_13, label = string.tyre_wet)
        )
    ),
    S2013_S2015(
        from = 2013,
        to = 2015,
        tyres = listOf(
            TyreLabel(tyre = RED_DRY_13, label = string.tyre_super_soft),
            TyreLabel(tyre = YELLOW_DRY_13, label = string.tyre_soft),
            TyreLabel(tyre = WHITE_DRY_13, label = string.tyre_medium),
            TyreLabel(tyre = ORANGE_DRY_13, label = string.tyre_hard),
            TyreLabel(tyre = GREEN_WET_13, label = string.tyre_intermediate),
            TyreLabel(tyre = BLUE_WET_13, label = string.tyre_wet)
        )
    ),
    S2016_S2017(
        from = 2016,
        to = 2017,
        tyres = listOf(
            TyreLabel(tyre = PURPLE_DRY_13, label = string.tyre_ultra_soft),
            TyreLabel(tyre = RED_DRY_13, label = string.tyre_super_soft),
            TyreLabel(tyre = YELLOW_DRY_13, label = string.tyre_soft),
            TyreLabel(tyre = WHITE_DRY_13, label = string.tyre_medium),
            TyreLabel(tyre = ORANGE_DRY_13, label = string.tyre_hard),
            TyreLabel(tyre = GREEN_WET_13, label = string.tyre_intermediate),
            TyreLabel(tyre = BLUE_WET_13, label = string.tyre_wet)
        )
    ),
    S2018(
        singleYear = 2018,
        tyres = listOf(
            TyreLabel(tyre = PINK_DRY_13, label = string.tyre_hyper_soft),
            TyreLabel(tyre = PURPLE_DRY_13, label = string.tyre_ultra_soft),
            TyreLabel(tyre = RED_DRY_13, label = string.tyre_super_soft),
            TyreLabel(tyre = YELLOW_DRY_13, label = string.tyre_soft),
            TyreLabel(tyre = WHITE_DRY_13, label = string.tyre_medium),
            TyreLabel(tyre = BLUE_DRY_13, label = string.tyre_hard),
            TyreLabel(tyre = ORANGE_DRY_13, label = string.tyre_super_hard),
            TyreLabel(tyre = GREEN_WET_13, label = string.tyre_intermediate),
            TyreLabel(tyre = BLUE_WET_13, label = string.tyre_wet)
        )
    ),
    S2019_S2021(
        from = 2019,
        to = 2021,
        tyres = listOf(
            TyreLabel(tyre = RED_DRY_13, label = string.tyre_soft),
            TyreLabel(tyre = YELLOW_DRY_13, label = string.tyre_medium),
            TyreLabel(tyre = WHITE_DRY_13, label = string.tyre_hard),
            TyreLabel(tyre = GREEN_WET_13, label = string.tyre_intermediate),
            TyreLabel(tyre = BLUE_WET_13, label = string.tyre_wet)
        )
    ),
    S2022(
        from = 2022,
        tyres = listOf(
            TyreLabel(tyre = RED_DRY_18, label = string.tyre_soft),
            TyreLabel(tyre = YELLOW_DRY_18, label = string.tyre_medium),
            TyreLabel(tyre = WHITE_DRY_18, label = string.tyre_hard),
            TyreLabel(tyre = GREEN_WET_18, label = string.tyre_intermediate),
            TyreLabel(tyre = BLUE_WET_18, label = string.tyre_wet)
        )
    );

    constructor(
        singleYear: Int,
        tyres: List<TyreLabel>
    ): this(
        from = singleYear,
        to = singleYear,
        tyres = tyres
    )

    fun includesYear(year: Int): Boolean {
        if (year >= from && to == null) {
            return true
        }
        if (year >= from && to != null && year <= to) {
            return true
        }
        return false
    }

    companion object
}
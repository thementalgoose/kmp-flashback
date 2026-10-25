package tmg.flashback.eastereggs.model

import flashback.eastereggs.generated.resources.Res
import flashback.eastereggs.generated.resources.ic_easteregg_bonfire
import flashback.eastereggs.generated.resources.ic_easteregg_christmas
import flashback.eastereggs.generated.resources.ic_easteregg_diwali
import flashback.eastereggs.generated.resources.ic_easteregg_easter
import flashback.eastereggs.generated.resources.ic_easteregg_halloween
import flashback.eastereggs.generated.resources.ic_easteregg_newyears
import flashback.eastereggs.generated.resources.ic_easteregg_st_patricks_day
import flashback.eastereggs.generated.resources.ic_easteregg_valentines
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.easter_egg_menu_bonfire_night
import flashback.presentation.localisation.generated.resources.easter_egg_menu_chinese_new_year
import flashback.presentation.localisation.generated.resources.easter_egg_menu_christmas
import flashback.presentation.localisation.generated.resources.easter_egg_menu_diwali
import flashback.presentation.localisation.generated.resources.easter_egg_menu_easter
import flashback.presentation.localisation.generated.resources.easter_egg_menu_halloween
import flashback.presentation.localisation.generated.resources.easter_egg_menu_independence_day
import flashback.presentation.localisation.generated.resources.easter_egg_menu_new_years
import flashback.presentation.localisation.generated.resources.easter_egg_menu_st_patricks_day
import flashback.presentation.localisation.generated.resources.easter_egg_menu_valentines
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.infrastructure.datetime.now

private val year: Int by lazy { LocalDate.now().year }

enum class MenuIcons(
    val key: String,
    val dateRanges: List<Pair<LocalDate, LocalDate>>,
    val label: StringResource?,
    val drawable: DrawableResource,
) {
    CHRISTMAS(
        key = "christmas",
        dateRanges = listOf(
            LocalDate(year, 12, 21) to LocalDate(year, 12, 25)
        ),
        label = string.easter_egg_menu_christmas,
        drawable = Res.drawable.ic_easteregg_christmas,
    ),
    NEW_YEARS(
        key = "new_year",
        dateRanges = listOf(
            LocalDate(year, 1, 1) to LocalDate(year, 1, 2)
        ),
        label = string.easter_egg_menu_new_years,
        drawable = Res.drawable.ic_easteregg_newyears
    ),
    CHINESE_NEW_YEAR(
        key = "new_year",
        dateRanges = listOf(
            LocalDate(2027, 2, 6) to LocalDate(2027, 2, 7),
            LocalDate(2028, 1, 26) to LocalDate(2028, 1, 27),
            LocalDate(2029, 2, 13) to LocalDate(2029, 2, 13),
            LocalDate(2030, 2, 3) to LocalDate(2030, 2, 4),
        ),
        label = string.easter_egg_menu_chinese_new_year,
        drawable = Res.drawable.ic_easteregg_newyears
    ),
    NEW_YEARS_EVE(
        key = "new_year_eve",
        dateRanges = listOf(
            LocalDate(year, 12, 31) to LocalDate(year, 12, 31)
        ),
        label = string.easter_egg_menu_new_years,
        drawable = Res.drawable.ic_easteregg_newyears
    ),
    EASTER(
        key = "easter",
        dateRanges = listOf(
            LocalDate(2027, 3, 26) to LocalDate(2027, 3, 28),
            LocalDate(2028, 4, 14) to LocalDate(2028, 4, 16),
            LocalDate(2029, 3, 29) to LocalDate(2029, 4, 1),
            LocalDate(2030, 4, 19) to LocalDate(2030, 4, 21)
        ),
        label = string.easter_egg_menu_easter,
        drawable = Res.drawable.ic_easteregg_easter
    ),
    DIWALI(
        key = "diwali",
        dateRanges = listOf(
            LocalDate(2026, 11, 6) to LocalDate(2026, 11, 8),
            LocalDate(2027, 10, 27) to LocalDate(2027, 10, 29),
            LocalDate(2028, 10, 15) to LocalDate(2028, 10, 17),
            LocalDate(2029, 11, 3) to LocalDate(2029, 11, 5),
            LocalDate(2030, 10, 24) to LocalDate(2030, 10, 26)
        ),
        label = string.easter_egg_menu_diwali,
        drawable = Res.drawable.ic_easteregg_diwali
    ),
    HALLOWEEN(
        key = "halloween",
        dateRanges = listOf(
            LocalDate(year, 10, 30) to LocalDate(year, 10, 31)
        ),
        label = string.easter_egg_menu_halloween,
        drawable = Res.drawable.ic_easteregg_halloween
    ),
    VALENTINES_DAY(
        key = "valentines",
        dateRanges = listOf(
            LocalDate(year, 2, 13) to LocalDate(year, 2, 14)
        ),
        label = string.easter_egg_menu_valentines,
        drawable = Res.drawable.ic_easteregg_valentines
    ),
    ST_PATRICKS_DAY(
        key = "st_patricks_day",
        dateRanges = listOf(
            LocalDate.now() to LocalDate.now(),
            LocalDate(year, 3, 17) to LocalDate(year, 3, 17)
        ),
        label = string.easter_egg_menu_st_patricks_day,
        drawable = Res.drawable.ic_easteregg_st_patricks_day
    ),
    INDEPENDENCE_DAY(
        key = "independence_day",
        dateRanges = listOf(
            LocalDate(year, 7, 4) to LocalDate(year, 7, 4)
        ),
        label = string.easter_egg_menu_independence_day,
        drawable = Res.drawable.ic_easteregg_bonfire
    ),
    BONFIRE(
        key = "bonfire",
        dateRanges = listOf(
            LocalDate(year, 11, 4) to LocalDate(year, 11, 5)
        ),
        label = string.easter_egg_menu_bonfire_night,
        drawable = Res.drawable.ic_easteregg_bonfire
    );

    fun isNow(now: LocalDate = LocalDate.now()): Boolean {
        for ((start, end) in dateRanges) {
            if (now in start..end) {
                return true
            }
        }
        return false
    }
}
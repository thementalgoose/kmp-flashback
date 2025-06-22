package tmg.flashback.eastereggs.model

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.*
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.infrastructure.datetime.now

private val year: Int by lazy { LocalDate.now().year }

enum class MenuIcons(
    val key: String,
    val startResolver: () -> LocalDate,
    val endResolver: () -> LocalDate,
    val label: StringResource?
) {
    NEW_YEARS(
        key = "new_year",
        startResolver = { LocalDate(year, 1, 1) },
        endResolver = { LocalDate(year, 1, 1) },
        label = string.easter_egg_menu_new_years
    ),
    CHINESE_NEW_YEAR(
        key = "new_year",
        startResolver = { LocalDate(2026, 2, 17) },
        endResolver = { LocalDate(2026, 3, 3) },
        label = string.easter_egg_menu_chinese_new_year
    ),
    VALENTINES_DAY(
        key = "valentines",
        startResolver = { LocalDate(year, 2, 12) },
        endResolver = { LocalDate(year, 2, 14) },
        label = string.easter_egg_menu_valentines
    ),
    EASTER(
        key = "easter",
        startResolver = { LocalDate(2025, 4, 18) },
        endResolver = { LocalDate(2025, 4, 21) },
        label = string.easter_egg_menu_easter
    ),
    HALLOWEEN(
        key = "halloween",
        startResolver = { LocalDate(year, 10, 21) },
        endResolver = { LocalDate(year, 10, 31) },
        label = string.easter_egg_menu_halloween
    ),
    BONFIRE(
        key = "bonfire",
        startResolver = { LocalDate(year, 11, 4) },
        endResolver = { LocalDate(year, 11, 5) },
        label = null
    ),
    DIWALI(
        key = "diwali",
        startResolver = { LocalDate(2025, 10, 19) },
        endResolver = { LocalDate(2025, 10, 21) },
        label = string.easter_egg_menu_diwali
    ),
    CHRISTMAS(
        key = "christmas",
        startResolver = { LocalDate(year, 12, 19) },
        endResolver = { LocalDate(year, 12, 25) },
        label = string.easter_egg_menu_christmas
    ),
    NEW_YEARS_EVE(
        key = "new_year_eve",
        startResolver = { LocalDate(year, 12, 31) },
        endResolver = { LocalDate(year, 12, 31) },
        label = string.easter_egg_menu_new_years
    );

    val start: LocalDate by lazy { startResolver() }
    val end: LocalDate by lazy { endResolver() }

    fun isNow(now: LocalDate = LocalDate.now()): Boolean{
        return now in start..end
    }
}
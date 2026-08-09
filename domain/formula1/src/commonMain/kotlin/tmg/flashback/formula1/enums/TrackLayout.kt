package tmg.flashback.formula1.enums

import flashback.domain.formula1.generated.resources.Res
import flashback.domain.formula1.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

/**
 * Supported track layouts
 */
enum class TrackLayout(
    val circuitId: String,
    private val icon: DrawableResource,
    val breakdown: TrackBreakdown? = null,
    private val overrides: List<Configuration> = emptyList(),
) {
    ADELAIDE(
        circuitId = "adelaide",
        icon = Res.drawable.circuit_adelaide
    ),
    AINTREE(
        circuitId = "aintree",
        icon = Res.drawable.circuit_aintree
    ),
    AIN_DIAB(
        circuitId = "ain-diab",
        icon = Res.drawable.circuit_ain_diab
    ),
    ALBERT_PARK(
        circuitId = "albert_park",
        icon = Res.drawable.circuit_albert_park,
        breakdown = TrackBreakdown.ALBERT_PARK
    ),
    ALGARVE(
        circuitId = "algarve",
        icon = Res.drawable.circuit_algarve
    ),
    ANDERSTORP(
        circuitId = "anderstorp",
        icon = Res.drawable.circuit_anderstorp
    ),
    AVUS(
        circuitId = "avus",
        icon = Res.drawable.circuit_avus
    ),
    PORTIMAO(
        circuitId = "portimao",
        icon = Res.drawable.circuit_algarve
    ),
    AMERICAS(
        circuitId = "americas",
        icon = Res.drawable.circuit_americas
    ),
    BUDDH(
        circuitId = "buddh",
        icon = Res.drawable.circuit_buddh),
    BAHRAIN(
        circuitId = "bahrain",
        icon = Res.drawable.circuit_bahrain,
        breakdown = null,
        overrides = listOf(
            Configuration.OneOff(2010, _icon = Res.drawable.circuit_bahrain_2010),
            Configuration.OneOff(2020, name = "Sakhir Grand Prix", _icon = Res.drawable.circuit_sakhir)
        )
    ),
    BAK(
        circuitId = "BAK",
        icon = Res.drawable.circuit_bak
    ),
    BREMGARTEN(
        circuitId = "bremgarten",
        icon = Res.drawable.circuit_bremgarten
    ),
    BRANDS_HATCH(
        circuitId = "brands_hatch",
        icon = Res.drawable.circuit_brands_hatch
    ),
    BOAVISTA(
        circuitId = "boavista",
        icon = Res.drawable.circuit_boavista
    ),
    CATALUNYA(
        circuitId = "catalunya",
        icon = Res.drawable.circuit_catalunya
    ),
    CATALUNYA_1991_2022(
        circuitId = "catalunya",
        icon = Res.drawable.circuit_catalunya_1991_2022
    ),
    CHARADE(
        circuitId = "charade",
        icon = Res.drawable.circuit_charade
    ),
    DALLAS(
        circuitId = "dallas",
        icon = Res.drawable.circuit_dallas
    ),
    DETROIT(
        circuitId = "detroit",
        icon = Res.drawable.circuit_detroit
    ),
    DIJON(
        circuitId = "dijon",
        icon = Res.drawable.circuit_dijon
    ),
    DONINGTON(
        circuitId = "donington",
        icon = Res.drawable.circuit_donington
    ),
    ESTORIL(
        circuitId = "estoril",
        icon = Res.drawable.circuit_estoril
    ),
    ESSARTS(
        circuitId = "essarts",
        icon = Res.drawable.circuit_essarts
    ),
    FAIR_PARK(
        circuitId = "fair_park",
        icon = Res.drawable.circuit_fair_park
    ),
    FUJI(
        circuitId = "fuji",
        icon = Res.drawable.circuit_fuji
    ),
    GALVEZ(
        circuitId = "galvez",
        icon = Res.drawable.circuit_galvez,
        breakdown = null,
        overrides = listOf(
            Configuration.Range(min = 1974, max = 1981, _icon = Res.drawable.circuit_galvez_1974_1981)
        )
    ),
    GEORGE(
        circuitId = "george",
        icon = Res.drawable.circuit_george
    ),
    HANOI(
        circuitId = "hanoi",
        icon = Res.drawable.circuit_hanoi
    ),
    HOCKENHEIMRING(
        circuitId = "hockenheimring",
        icon = Res.drawable.circuit_hockenheimring
    ),
    HUNGARORING(
        circuitId = "hungaroring",
        icon = Res.drawable.circuit_hungaroring
    ),
    INDIANAPOLIS(
        circuitId = "indianapolis",
        icon = Res.drawable.circuit_indianapolis
    ),
    INTERLAGOS(
        circuitId = "interlagos",
        icon = Res.drawable.circuit_interlagos
    ),
    IMOLA(
        circuitId = "imola",
        icon = Res.drawable.circuit_imola,
        breakdown = null,
        overrides = listOf(
            Configuration.Range(min = 1995, max = 2006, _icon = Res.drawable.circuit_imola_1995_2006),
            Configuration.Range(min = 1980, max = 1994, _icon = Res.drawable.circuit_imola_1980_1994),
        )
    ),
    ISTANBUL(
        circuitId = "istanbul",
        icon = Res.drawable.circuit_istanbul
    ),
    JACAREPAGUA(
        circuitId = "jacarepagua",
        icon = Res.drawable.circuit_jacarepagua
    ),
    JARAMA(
        circuitId = "jarama",
        icon = Res.drawable.circuit_jarama
    ),
    JEDDAH(
        circuitId = "jeddah",
        icon = Res.drawable.circuit_jeddah
    ),
    JEREZ(
        circuitId = "jerez",
        icon = Res.drawable.circuit_jerez
    ),
    KYALAMI(
        circuitId = "kyalami",
        icon = Res.drawable.circuit_kyalami
    ),
    LAS_VEGAS(
        circuitId = "las_vegas",
        icon = Res.drawable.circuit_las_vegas_1981_1982
    ),
    LEMANS(
        circuitId = "lemans",
        icon = Res.drawable.circuit_lemans
    ),
    LONG_BEACH(
        circuitId = "long_beach",
        icon = Res.drawable.circuit_long_beach
    ),
    LOSAIL(
        circuitId = "losail",
        icon = Res.drawable.circuit_losail
    ),
    MADRING(
        circuitId = "madring",
        icon = Res.drawable.circuit_madring
    ),
    MAGNY_COURS(
        circuitId = "magny_cours",
        icon = Res.drawable.circuit_magny_cours
    ),
    MARINA_BAY(
        circuitId = "marina_bay",
        icon = Res.drawable.circuit_marina_bay,
        breakdown = null,
        overrides = listOf(
            Configuration.Range(min = 2008, max = 2022, _icon = Res.drawable.circuit_marina_bay_2008_2022)
        )
    ),
    MIAMI(
        circuitId = "miami",
        icon = Res.drawable.circuit_miami,
        breakdown = TrackBreakdown.MIAMI
    ),
    MONACO(
        circuitId = "monaco",
        icon = Res.drawable.circuit_monaco,
        breakdown = TrackBreakdown.MONACO
    ),
    MONZA(
        circuitId = "monza",
        icon = Res.drawable.circuit_monza
    ),
    MONSANTO(
        circuitId = "monsanto",
        icon = Res.drawable.circuit_monsanto
    ),
    MONTJUIC(
        circuitId = "montjuic",
        icon = Res.drawable.circuit_montjuic
    ),
    MOSPORT(
        circuitId = "mosport",
        icon = Res.drawable.circuit_mosport
    ),
    MUGELLO(
        circuitId = "mugello",
        icon = Res.drawable.circuit_mugello
    ),
    NIVELLES(
        circuitId = "nivelles",
        icon = Res.drawable.circuit_nivelles
    ),
    NURBURGRING(
        circuitId = "nurburgring",
        icon = Res.drawable.circuit_nurburgring
    ),
    OKAYAMA(
        circuitId = "okayama",
        icon = Res.drawable.circuit_okayama
    ),
    OSTERREICHRING(
        circuitId = "osterreichring",
        icon = Res.drawable.circuit_osterreichring
    ),
    PESCARA(
        circuitId = "pescara",
        icon = Res.drawable.circuit_pescara
    ),
    PEDRALBES(
        circuitId = "pedralbes",
        icon = Res.drawable.circuit_pedralbes
    ),
    PHOENIX(
        circuitId = "phoenix",
        icon = Res.drawable.circuit_phoenix
    ),
    PORT_IMPERIAL(
        circuitId = "port_imperial",
        icon = Res.drawable.circuit_port_imperial
    ),
    RED_BULL_RING(
        circuitId = "red_bull_ring",
        icon = Res.drawable.circuit_red_bull_ring
    ),
    REIMS(
        circuitId = "reims",
        icon = Res.drawable.circuit_reims
    ),
    RICARD(
        circuitId = "ricard",
        icon = Res.drawable.circuit_ricard
    ),
    RIVERSIDE(
        circuitId = "riverside",
        icon = Res.drawable.circuit_riverside
    ),
    RODRIGUEZ(
        circuitId = "rodriguez",
        icon = Res.drawable.circuit_rodriguez
    ),
    SEBRING(
        circuitId = "sebring",
        icon = Res.drawable.circuit_sebring
    ),
    SEPANG(
        circuitId = "sepang",
        icon = Res.drawable.circuit_sepang
    ),
    SHANGHAI(
        circuitId = "shanghai",
        icon = Res.drawable.circuit_shanghai,
        breakdown = TrackBreakdown.SHANGHAI
    ),
    SILVERSTONE(
        circuitId = "silverstone",
        icon = Res.drawable.circuit_silverstone,
        breakdown = null,
        overrides = listOf(
            Configuration.Range(min = 1997, max = 2009, _icon = Res.drawable.circuit_silverstone_1997_2009),
            Configuration.OneOff(year = 1996, _icon = Res.drawable.circuit_silverstone_1996),
            Configuration.Range(min = 1994, max = 1995, _icon = Res.drawable.circuit_silverstone_1994_1995),
            Configuration.Range(min = 1991, max = 1993, _icon = Res.drawable.circuit_silverstone_1991_1993),
            Configuration.Range(min = 1987, max = 1990, _icon = Res.drawable.circuit_silverstone_1987_1990),
            Configuration.Range(min = 1975, max = 1986, _icon = Res.drawable.circuit_silverstone_1975_1986),
            Configuration.Range(min = 1950, max = 1973, _icon = Res.drawable.circuit_silverstone_1950_1973)
        )
    ),
    SOCHI(
        circuitId = "sochi",
        icon = Res.drawable.circuit_sochi
    ),
    SPA(
        circuitId = "spa",
        icon = Res.drawable.circuit_spa
    ),
    SUZUKA(
        circuitId = "suzuka",
        icon = Res.drawable.circuit_suzuka,
        breakdown = TrackBreakdown.SUZUKA
    ),
    TREMBLANT(
        circuitId = "tremblant",
        icon = Res.drawable.circuit_tremblant
    ),
    VALENCIA(
        circuitId = "valencia",
        icon = Res.drawable.circuit_valencia
    ),
    VEGAS(
        circuitId = "vegas",
        icon = Res.drawable.circuit_vegas
    ),
    VILLENEUVE(
        circuitId = "villeneuve",
        icon = Res.drawable.circuit_villeneuve,
        breakdown = TrackBreakdown.VILLENEUVE
    ),
    WATKINS_GLEN(
        circuitId = "watkins_glen",
        icon = Res.drawable.circuit_watkins_glen
    ),
    YAS_MARINA(
        circuitId = "yas_marina",
        icon = Res.drawable.circuit_yas_marina,
        breakdown = null,
        overrides = listOf(
            Configuration.Range(min = 2009, max = 2020, _icon = Res.drawable.circuit_yas_marina_2009_2020)
        )
    ),
    YEONGAM(
        circuitId = "yeongam",
        icon = Res.drawable.circuit_yeongam
    ),
    ZANDVOORT(
        circuitId = "zandvoort",
        icon = Res.drawable.circuit_zandvoort
    ),
    ZELTWEG(
        circuitId = "zeltweg",
        icon = Res.drawable.circuit_zeltweg
    ),
    ZOLDER(
        circuitId = "zolder",
        icon = Res.drawable.circuit_zolder
    );

    fun getDefaultIcon(): DrawableResource {
        return icon
    }

    fun getIcon(year: Int, name: String): DrawableResource {
        if (overrides.isEmpty()) {
            return icon
        }

        val override = overrides
            .firstOrNull { option ->
                return@firstOrNull when (option) {
                    is Configuration.OneOff -> if (option.name != null) {
                        year == option.year && name == option.name
                    } else {
                        year == option.year
                    }
                    is Configuration.Range -> year <= option.max && year >= option.min
                }
            }
        if (override != null) {
            return override.icon
        } else {
            return icon
        }
    }

    companion object {

        fun getTrack(circuitId: String): TrackLayout? {
            return entries.firstOrNull { it.circuitId == circuitId }
        }
    }
}

sealed class Configuration(
    val icon: DrawableResource
){

    data class OneOff(
        val year: Int,
        val name: String? = null,
        private val _icon: DrawableResource
    ): Configuration(_icon)

    data class Range(
        val min: Int,
        val max: Int,
        private val _icon: DrawableResource
    ): Configuration(_icon)
}
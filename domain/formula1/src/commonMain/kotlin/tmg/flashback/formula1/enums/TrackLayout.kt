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
    private vararg val overrides: Configuration,
) {
    ADELAIDE("adelaide", Res.drawable.circuit_adelaide),
    AINTREE("aintree", Res.drawable.circuit_aintree),
    AIN_DIAB("ain-diab", Res.drawable.circuit_ain_diab),
    ALBERT_PARK("albert_park", Res.drawable.circuit_albert_park),
    ALGARVE("algarve", Res.drawable.circuit_algarve),
    ANDERSTORP("anderstorp", Res.drawable.circuit_anderstorp),
    AVUS("avus", Res.drawable.circuit_avus),
    PORTIMAO("portimao", Res.drawable.circuit_algarve),
    AMERICAS("americas", Res.drawable.circuit_americas),
    BUDDH("buddh", Res.drawable.circuit_buddh),
    BAHRAIN("bahrain", Res.drawable.circuit_bahrain,
        Configuration.OneOff(2010, _icon = Res.drawable.circuit_bahrain_2010),
        Configuration.OneOff(2020, name = "Sakhir Grand Prix", _icon = Res.drawable.circuit_sakhir)
    ),
    BAK("BAK", Res.drawable.circuit_bak),
    BREMGARTEN("bremgarten", Res.drawable.circuit_bremgarten),
    BRANDS_HATCH("brands_hatch", Res.drawable.circuit_brands_hatch),
    BOAVISTA("boavista", Res.drawable.circuit_boavista),
    CATALUNYA("catalunya", Res.drawable.circuit_catalunya),
    CATALUNYA_1991_2022("catalunya", Res.drawable.circuit_catalunya_1991_2022),
    CHARADE("charade", Res.drawable.circuit_charade),
    DALLAS("dallas", Res.drawable.circuit_dallas),
    DETROIT("detroit", Res.drawable.circuit_detroit),
    DIJON("dijon", Res.drawable.circuit_dijon),
    DONINGTON("donington", Res.drawable.circuit_donington),
    ESTORIL("estoril", Res.drawable.circuit_estoril),
    ESSARTS("essarts", Res.drawable.circuit_essarts),
    FAIR_PARK("fair_park", Res.drawable.circuit_fair_park),
    FUJI("fuji", Res.drawable.circuit_fuji),
    GALVEZ("galvez", Res.drawable.circuit_galvez,
        Configuration.Range(min = 1974, max = 1981, _icon = Res.drawable.circuit_galvez_1974_1981)
    ),
    GEORGE("george", Res.drawable.circuit_george),
    HANOI("hanoi", Res.drawable.circuit_hanoi),
    HOCKENHEIMRING("hockenheimring", Res.drawable.circuit_hockenheimring),
    HUNGARORING("hungaroring", Res.drawable.circuit_hungaroring),
    INDIANAPOLIS("indianapolis", Res.drawable.circuit_indianapolis),
    INTERLAGOS("interlagos", Res.drawable.circuit_interlagos),
    IMOLA("imola", Res.drawable.circuit_imola,
        Configuration.Range(min = 1995, max = 2006, _icon = Res.drawable.circuit_imola_1995_2006),
        Configuration.Range(min = 1980, max = 1994, _icon = Res.drawable.circuit_imola_1980_1994),
    ),
    ISTANBUL("istanbul", Res.drawable.circuit_istanbul),
    JACAREPAGUA("jacarepagua", Res.drawable.circuit_jacarepagua),
    JARAMA("jarama", Res.drawable.circuit_jarama),
    JEDDAH("jeddah", Res.drawable.circuit_jeddah),
    JEREZ("jerez", Res.drawable.circuit_jerez),
    KYALAMI("kyalami", Res.drawable.circuit_kyalami),
    LAS_VEGAS("las_vegas", Res.drawable.circuit_las_vegas_1981_1982),
    LEMANS("lemans", Res.drawable.circuit_lemans),
    LONG_BEACH("long_beach", Res.drawable.circuit_long_beach),
    LOSAIL("losail", Res.drawable.circuit_losail),
    MADRING("madring", Res.drawable.circuit_madring),
    MAGNY_COURS("magny_cours", Res.drawable.circuit_magny_cours),
    MARINA_BAY("marina_bay", Res.drawable.circuit_marina_bay,
        Configuration.Range(min = 2008, max = 2022, _icon = Res.drawable.circuit_marina_bay_2008_2022)
    ),
    MIAMI("miami", Res.drawable.circuit_miami),
    MONACO("monaco", Res.drawable.circuit_monaco),
    MONZA("monza", Res.drawable.circuit_monza),
    MONSANTO("monsanto", Res.drawable.circuit_monsanto),
    MONTJUIC("montjuic", Res.drawable.circuit_montjuic),
    MOSPORT("mosport", Res.drawable.circuit_mosport),
    MUGELLO("mugello", Res.drawable.circuit_mugello),
    NIVELLES("nivelles", Res.drawable.circuit_nivelles),
    NURBURGRING("nurburgring", Res.drawable.circuit_nurburgring),
    OKAYAMA("okayama", Res.drawable.circuit_okayama),
    OSTERREICHRING("osterreichring", Res.drawable.circuit_osterreichring),
    PESCARA("pescara", Res.drawable.circuit_pescara),
    PEDRALBES("pedralbes", Res.drawable.circuit_pedralbes),
    PHOENIX("phoenix", Res.drawable.circuit_phoenix),
    PORT_IMPERIAL("port_imperial", Res.drawable.circuit_port_imperial),
    RED_BULL_RING("red_bull_ring", Res.drawable.circuit_red_bull_ring),
    REIMS("reims", Res.drawable.circuit_reims),
    RICARD("ricard", Res.drawable.circuit_ricard),
    RIVERSIDE("riverside", Res.drawable.circuit_riverside),
    RODRIGUEZ("rodriguez", Res.drawable.circuit_rodriguez),
    SEBRING("sebring", Res.drawable.circuit_sebring),
    SEPANG("sepang", Res.drawable.circuit_sepang),
    SHANGHAI("shanghai", Res.drawable.circuit_shanghai),
    SILVERSTONE("silverstone", Res.drawable.circuit_silverstone,
        Configuration.Range(min = 1997, max = 2009, _icon = Res.drawable.circuit_silverstone_1997_2009),
        Configuration.OneOff(year = 1996, _icon = Res.drawable.circuit_silverstone_1996),
        Configuration.Range(min = 1994, max = 1995, _icon = Res.drawable.circuit_silverstone_1994_1995),
        Configuration.Range(min = 1991, max = 1993, _icon = Res.drawable.circuit_silverstone_1991_1993),
        Configuration.Range(min = 1987, max = 1990, _icon = Res.drawable.circuit_silverstone_1987_1990),
        Configuration.Range(min = 1975, max = 1986, _icon = Res.drawable.circuit_silverstone_1975_1986),
        Configuration.Range(min = 1950, max = 1973, _icon = Res.drawable.circuit_silverstone_1950_1973)
    ),
    SOCHI("sochi", Res.drawable.circuit_sochi),
    SPA("spa", Res.drawable.circuit_spa),
    SUZUKA("suzuka", Res.drawable.circuit_suzuka),
    TREMBLANT("tremblant", Res.drawable.circuit_tremblant),
    VALENCIA("valencia", Res.drawable.circuit_valencia),
    VEGAS("vegas", Res.drawable.circuit_vegas),
    VILLENEUVE("villeneuve", Res.drawable.circuit_villeneuve),
    WATKINS_GLEN("watkins_glen", Res.drawable.circuit_watkins_glen),
    YAS_MARINA("yas_marina", Res.drawable.circuit_yas_marina,
        Configuration.Range(min = 2009, max = 2020, Res.drawable.circuit_yas_marina_2009_2020)
    ),
    YEONGAM("yeongam", Res.drawable.circuit_yeongam),
    ZANDVOORT("zandvoort", Res.drawable.circuit_zandvoort),
    ZELTWEG("zeltweg", Res.drawable.circuit_zeltweg),
    ZOLDER("zolder", Res.drawable.circuit_zolder);

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
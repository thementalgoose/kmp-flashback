package tmg.flashback.widgets.upnext.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import tmg.flashback.feature.widget_upnext.R
import tmg.flashback.formula1.enums.TrackLayout

@get:DrawableRes
val TrackLayout.icon: Int
    get() = when (this) {
        TrackLayout.ADELAIDE -> R.drawable.circuit_adelaide
        TrackLayout.AINTREE -> R.drawable.circuit_aintree
        TrackLayout.AIN_DIAB -> R.drawable.circuit_ain_diab
        TrackLayout.ALBERT_PARK -> R.drawable.circuit_albert_park
        TrackLayout.ALGARVE -> R.drawable.circuit_algarve
        TrackLayout.ANDERSTORP -> R.drawable.circuit_anderstorp
        TrackLayout.AVUS -> R.drawable.circuit_avus
        TrackLayout.PORTIMAO -> R.drawable.circuit_algarve
        TrackLayout.AMERICAS -> R.drawable.circuit_americas
        TrackLayout.BUDDH -> R.drawable.circuit_buddh
        TrackLayout.BAHRAIN -> R.drawable.circuit_bahrain
        TrackLayout.BAK -> R.drawable.circuit_bak
        TrackLayout.BREMGARTEN -> R.drawable.circuit_bremgarten
        TrackLayout.BRANDS_HATCH -> R.drawable.circuit_brands_hatch
        TrackLayout.BOAVISTA -> R.drawable.circuit_boavista
        TrackLayout.CATALUNYA -> R.drawable.circuit_catalunya
        TrackLayout.CATALUNYA_1991_2022 -> R.drawable.circuit_catalunya
        TrackLayout.CHARADE -> R.drawable.circuit_charade
        TrackLayout.DALLAS -> R.drawable.circuit_dallas
        TrackLayout.DETROIT -> R.drawable.circuit_detroit
        TrackLayout.DIJON -> R.drawable.circuit_dijon
        TrackLayout.DONINGTON -> R.drawable.circuit_donington
        TrackLayout.ESTORIL -> R.drawable.circuit_estoril
        TrackLayout.ESSARTS -> R.drawable.circuit_essarts
        TrackLayout.FAIR_PARK -> R.drawable.circuit_fair_park
        TrackLayout.FUJI -> R.drawable.circuit_fuji
        TrackLayout.GALVEZ -> R.drawable.circuit_galvez
        TrackLayout.GEORGE -> R.drawable.circuit_george
        TrackLayout.HANOI -> R.drawable.circuit_hanoi
        TrackLayout.HOCKENHEIMRING -> R.drawable.circuit_hockenheimring
        TrackLayout.HUNGARORING -> R.drawable.circuit_hungaroring
        TrackLayout.INDIANAPOLIS -> R.drawable.circuit_indianapolis
        TrackLayout.INTERLAGOS -> R.drawable.circuit_interlagos
        TrackLayout.IMOLA -> R.drawable.circuit_imola
        TrackLayout.ISTANBUL -> R.drawable.circuit_istanbul
        TrackLayout.JACAREPAGUA -> R.drawable.circuit_jacarepagua
        TrackLayout.JARAMA -> R.drawable.circuit_jarama
        TrackLayout.JEDDAH -> R.drawable.circuit_jeddah
        TrackLayout.JEREZ -> R.drawable.circuit_jerez
        TrackLayout.KYALAMI -> R.drawable.circuit_kyalami
        TrackLayout.LAS_VEGAS -> R.drawable.circuit_vegas
        TrackLayout.LEMANS -> R.drawable.circuit_lemans
        TrackLayout.LONG_BEACH -> R.drawable.circuit_long_beach
        TrackLayout.LOSAIL -> R.drawable.circuit_losail
        TrackLayout.MAGNY_COURS -> R.drawable.circuit_magny_cours
        TrackLayout.MARINA_BAY -> R.drawable.circuit_marina_bay
        TrackLayout.MIAMI -> R.drawable.circuit_miami
        TrackLayout.MONACO -> R.drawable.circuit_monaco
        TrackLayout.MONZA -> R.drawable.circuit_monza
        TrackLayout.MONSANTO -> R.drawable.circuit_monsanto
        TrackLayout.MONTJUIC -> R.drawable.circuit_montjuic
        TrackLayout.MOSPORT -> R.drawable.circuit_mosport
        TrackLayout.MUGELLO -> R.drawable.circuit_mugello
        TrackLayout.NIVELLES -> R.drawable.circuit_nivelles
        TrackLayout.NURBURGRING -> R.drawable.circuit_nurburgring
        TrackLayout.OKAYAMA -> R.drawable.circuit_okayama
        TrackLayout.OSTERREICHRING -> R.drawable.circuit_osterreichring
        TrackLayout.PESCARA -> R.drawable.circuit_pescara
        TrackLayout.PEDRALBES -> R.drawable.circuit_pedralbes
        TrackLayout.PHOENIX -> R.drawable.circuit_phoenix
        TrackLayout.PORT_IMPERIAL -> R.drawable.circuit_port_imperial
        TrackLayout.RED_BULL_RING -> R.drawable.circuit_red_bull_ring
        TrackLayout.REIMS -> R.drawable.circuit_reims
        TrackLayout.RICARD -> R.drawable.circuit_ricard
        TrackLayout.RIVERSIDE -> R.drawable.circuit_riverside
        TrackLayout.RODRIGUEZ -> R.drawable.circuit_rodriguez
        TrackLayout.SEBRING -> R.drawable.circuit_sebring
        TrackLayout.SEPANG -> R.drawable.circuit_sepang
        TrackLayout.SHANGHAI -> R.drawable.circuit_shanghai
        TrackLayout.SILVERSTONE -> R.drawable.circuit_silverstone
        TrackLayout.SOCHI -> R.drawable.circuit_sochi
        TrackLayout.SPA -> R.drawable.circuit_spa
        TrackLayout.SUZUKA -> R.drawable.circuit_suzuka
        TrackLayout.TREMBLANT -> R.drawable.circuit_tremblant
        TrackLayout.VALENCIA -> R.drawable.circuit_valencia
        TrackLayout.VEGAS -> R.drawable.circuit_vegas
        TrackLayout.VILLENEUVE -> R.drawable.circuit_villeneuve
        TrackLayout.WATKINS_GLEN -> R.drawable.circuit_watkins_glen
        TrackLayout.YAS_MARINA -> R.drawable.circuit_yas_marina
        TrackLayout.YEONGAM -> R.drawable.circuit_yeongam
        TrackLayout.ZANDVOORT -> R.drawable.circuit_zandvoort
        TrackLayout.ZELTWEG -> R.drawable.circuit_zeltweg
        TrackLayout.ZOLDER -> R.drawable.circuit_zolder
    }
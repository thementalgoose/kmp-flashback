package tmg.flashback.device.usecases

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.infrastructure.log.logInfo

actual class OpenLocationUseCaseImpl actual constructor(): OpenLocationUseCase, KoinComponent {

    private val applicationContext: Context by inject()

    private val openWebpageUseCase: OpenWebpageUseCase by inject()

    actual override fun invoke(lat: Double, lng: Double, name: String?) {
        val geoIntent = "geo:0,0?q=${lat},${lng} (${name})"
        val googleMapsLink = "https://google.com/maps/search/?api=1&query=$lat,$lng"
        when {
            isPackageInstalled(PACKAGE_GOOGLE_MAPS) -> {
                logInfo("Opening location with google maps intent $geoIntent")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoIntent))
                    .apply { setPackage(PACKAGE_GOOGLE_MAPS) }
                val chooser = Intent.createChooser(intent, "Maps")
                applicationContext.startActivity(chooser)
            }
            isPackageInstalled(PACKAGE_WAZE) -> {
                logInfo("Opening location with waze maps intent $geoIntent")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoIntent))
                    .apply { setPackage(PACKAGE_WAZE) }
                val chooser = Intent.createChooser(intent, "Maps")
                applicationContext.startActivity(chooser)
            }
            else -> {
                logInfo("Opening location with webpage intent $googleMapsLink")
                openWebpageUseCase(googleMapsLink)
            }
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean = try {
        applicationContext.packageManager.getApplicationInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    } catch (e: RuntimeException) {
        false
    }

    companion object {
        const val PACKAGE_GOOGLE_MAPS = "com.google.android.apps.maps"
        const val PACKAGE_WAZE = "com.waze"
    }
}
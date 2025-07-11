package tmg.flashback.device.usecases

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class OpenLocationUseCaseImpl actual constructor(): OpenLocationUseCase {
    actual override fun invoke(lat: Double, lng: Double, name: String?) {
        val url = NSURL(string = "http://maps.apple.com/maps?saddr=$lat,$lng")
        UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {

        })
    }
}
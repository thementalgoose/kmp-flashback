package tmg.flashback.device.usecases

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase {
    actual override fun invoke(email: String) {
        val url = NSURL(string = "mailto:$email")
        UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {

        })
    }
}
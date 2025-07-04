package tmg.flashback.device.usecases

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class OpenWebpageUseCaseImpl actual constructor(): OpenWebpageUseCase {
    actual override fun invoke(url: String, title: String) {
        val url = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication().openURL(url)
    }
}
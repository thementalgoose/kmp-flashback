package tmg.flashback.device.usecases

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import tmg.flashback.device.APPLE_STORE_LINK

actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase {
    actual override fun invoke() {
        val url = NSURL.URLWithString(APPLE_STORE_LINK) ?: return
        UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {

        })
    }
}
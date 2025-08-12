package tmg.flashback.device.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIPasteboard
import tmg.flashback.device.APPLE_STORE_LINK
import tmg.flashback.device.manager.UiManager
import kotlin.getValue

actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase, KoinComponent {

    private val uiManager: UiManager by inject()

    actual override fun invoke() {
        val url = NSURL.URLWithString(APPLE_STORE_LINK) ?: return
        UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {
            if (!it) {
                UIPasteboard.generalPasteboard.string = APPLE_STORE_LINK
                uiManager.showToUser("Copied to clipboard")
            }
        })
    }
}
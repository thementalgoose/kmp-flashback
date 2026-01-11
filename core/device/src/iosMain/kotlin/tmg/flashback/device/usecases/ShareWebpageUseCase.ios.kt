package tmg.flashback.device.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIPasteboard
import tmg.flashback.device.manager.UiManager
import kotlin.getValue

actual class ShareWebpageUseCaseImpl actual constructor() : ShareWebpageUseCase, KoinComponent {

    private val uiManager: UiManager by inject()

    actual override operator fun invoke(url: String, title: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return
        UIApplication.sharedApplication().openURL(nsUrl, options = emptyMap<Any?, Any>(), completionHandler = {
            if (!it) {
                UIPasteboard.generalPasteboard.string = url
                uiManager.showToUser("Copied to clipboard")
            }
        })
    }
}
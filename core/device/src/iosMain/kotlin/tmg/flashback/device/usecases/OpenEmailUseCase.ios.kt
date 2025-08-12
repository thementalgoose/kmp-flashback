package tmg.flashback.device.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIPasteboard
import tmg.flashback.device.manager.UiManager
import tmg.flashback.infrastructure.log.logInfo

actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase, KoinComponent {

    private val uiManager: UiManager by inject()

    actual override fun invoke(email: String) {
        val url = NSURL(string = "mailto:$email")
        UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {
            logInfo("Email", "Opening mailto url = $it")
            if (!it) {
                UIPasteboard.generalPasteboard.string = email
                uiManager.showToUser("Copied to clipboard")
            }
        })
    }
}
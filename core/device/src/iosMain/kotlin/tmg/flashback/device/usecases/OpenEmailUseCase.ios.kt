package tmg.flashback.device.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSURL
import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLQueryItem
import platform.UIKit.UIApplication
import platform.UIKit.UIPasteboard
import tmg.flashback.device.manager.UiManager
import tmg.flashback.infrastructure.log.logInfo
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.email_copy_to_clipboard

actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase, KoinComponent {

    private val uiManager: UiManager by inject()

    actual override fun invoke(
        email: String,
        title: String,
        contents: String
    ) {

        val urlComponents = NSURLComponents()
        urlComponents.setScheme("mailto")
        urlComponents.setPath(email)
        urlComponents.setQueryItems(listOf(
            NSURLQueryItem("subject", title.ifEmpty { "Flashback" }),
            NSURLQueryItem("body", contents)
        ))

        val url = urlComponents.URL
        if (url != null && UIApplication.sharedApplication().canOpenURL(url)) {
            UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {
                logInfo("Email", "Opening mailto url = $it")
                if (!it) {
                    UIPasteboard.generalPasteboard.string = email
                    uiManager.showToUser(string.email_copy_to_clipboard)
                }
            })
        } else {
            UIPasteboard.generalPasteboard.string = email
            uiManager.showToUser(string.email_copy_to_clipboard)
        }
    }
}
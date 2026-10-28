package tmg.flashback.device.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIPasteboard
import tmg.flashback.device.manager.UiManager
import tmg.flashback.device.repositories.DeviceRepository
import kotlin.getValue

actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase, KoinComponent {

    private val uiManager: UiManager by inject()
    private val deviceRepository: DeviceRepository by inject()

    actual override fun invoke() {
        val url = NSURL.URLWithString(deviceRepository.appleAppStoreLink) ?: return
        UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {
            if (!it) {
                UIPasteboard.generalPasteboard.string = deviceRepository.appleAppStoreLink
                uiManager.showToUser("Copied to clipboard")
            }
        })
    }
}
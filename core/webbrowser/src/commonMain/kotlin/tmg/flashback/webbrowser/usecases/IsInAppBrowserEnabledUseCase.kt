package tmg.flashback.webbrowser.usecases

import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform.Android

interface IsInAppBrowserEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsInAppBrowserEnabledUseCaseImpl(): IsInAppBrowserEnabledUseCase {
    override operator fun invoke(): Boolean {
        return Device.platform == Android
    }
}
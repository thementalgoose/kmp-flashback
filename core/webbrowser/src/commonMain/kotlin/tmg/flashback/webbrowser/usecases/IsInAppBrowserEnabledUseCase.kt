package tmg.flashback.webbrowser.usecases

import org.koin.core.annotation.Single
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform.Android
import tmg.flashback.webbrowser.repository.WebRepository

interface IsInAppBrowserEnabledUseCase {
    operator fun invoke(): Boolean
}

@Single
internal class IsInAppBrowserEnabledUseCaseImpl(): IsInAppBrowserEnabledUseCase {
    override operator fun invoke(): Boolean {
        return Device.platform == Android
    }
}
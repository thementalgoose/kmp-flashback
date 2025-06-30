package tmg.flashback.widgets.upnext.usecases

import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform

interface IsWidgetsEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsWidgetsEnabledUseCaseImpl(): IsWidgetsEnabledUseCase {
    override fun invoke(): Boolean {
        return Device.platform == Platform.Android
    }
}
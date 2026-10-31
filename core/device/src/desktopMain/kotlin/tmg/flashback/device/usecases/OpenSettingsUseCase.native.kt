package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [OpenSettingsUseCase::class])
actual class OpenSettingsUseCaseImpl actual constructor(): OpenSettingsUseCase {
    actual override fun openNotificationSettings() { }
    actual override fun openSettings() { }
    actual override fun openAlarmSettings() { }
}
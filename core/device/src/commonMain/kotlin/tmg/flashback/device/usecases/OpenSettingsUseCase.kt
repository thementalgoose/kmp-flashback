package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

interface OpenSettingsUseCase {
    fun openNotificationSettings()
    fun openSettings()
    fun openAlarmSettings()
}

@Single(binds = [OpenSettingsUseCase::class])
expect class OpenSettingsUseCaseImpl(): OpenSettingsUseCase {
    override fun openNotificationSettings()
    override fun openSettings()
    override fun openAlarmSettings()
}
package tmg.flashback.device.usecases

interface OpenSettingsUseCase {
    fun openNotificationSettings()
    fun openSettings()
    fun openAlarmSettings()
}

expect class OpenSettingsUseCaseImpl(): OpenSettingsUseCase {
    override fun openNotificationSettings()
    override fun openSettings()
    override fun openAlarmSettings()
}
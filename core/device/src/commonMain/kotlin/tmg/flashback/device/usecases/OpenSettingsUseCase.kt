package tmg.flashback.device.usecases

interface OpenSettingsUseCase {
    fun openNotificationSettings()
    fun openSettings()
}

expect class OpenSettingsUseCaseImpl(): OpenSettingsUseCase {
    override fun openNotificationSettings()
    override fun openSettings()
}
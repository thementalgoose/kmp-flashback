package tmg.flashback.device.usecases

actual class CopyToClipboardUseCaseImpl actual constructor() : CopyToClipboardUseCase {
    actual override fun invoke(text: String) { }
}

actual class OpenEmailUseCaseImpl actual constructor() : OpenEmailUseCase {
    actual override fun invoke(email: String, title: String, contents: String) { }
}

actual class OpenLocationUseCaseImpl actual constructor() : OpenLocationUseCase {
    actual override fun invoke(lat: Double, lng: Double, name: String?) { }
}

actual class OpenSettingsUseCaseImpl actual constructor() : OpenSettingsUseCase {
    actual override fun openNotificationSettings() { }
    actual override fun openSettings() { }
    actual override fun openAlarmSettings() { }
}

actual class OpenStorePageUseCaseImpl actual constructor() : OpenStorePageUseCase {
    actual override fun invoke() { }
}

actual class OpenWebpageUseCaseImpl actual constructor() : OpenWebpageUseCase {
    actual override fun invoke(url: String, title: String) { }
}

actual class ShareWebpageUseCaseImpl actual constructor() : ShareWebpageUseCase {
    actual override fun invoke(url: String, title: String) { }
}

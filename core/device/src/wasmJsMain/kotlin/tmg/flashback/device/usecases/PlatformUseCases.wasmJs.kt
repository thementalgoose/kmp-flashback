package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [CopyToClipboardUseCase::class])
actual class CopyToClipboardUseCaseImpl actual constructor() : CopyToClipboardUseCase {
    actual override fun invoke(text: String) { }
}

@Single(binds = [OpenEmailUseCase::class])
actual class OpenEmailUseCaseImpl actual constructor() : OpenEmailUseCase {
    actual override fun invoke(email: String, title: String, contents: String) { }
}

@Single(binds = [OpenLocationUseCase::class])
actual class OpenLocationUseCaseImpl actual constructor() : OpenLocationUseCase {
    actual override fun invoke(lat: Double, lng: Double, name: String?) { }
}

@Single(binds = [OpenSettingsUseCase::class])
actual class OpenSettingsUseCaseImpl actual constructor() : OpenSettingsUseCase {
    actual override fun openNotificationSettings() { }
    actual override fun openSettings() { }
    actual override fun openAlarmSettings() { }
}

@Single(binds = [OpenStorePageUseCase::class])
actual class OpenStorePageUseCaseImpl actual constructor() : OpenStorePageUseCase {
    actual override fun invoke() { }
}

@Single(binds = [OpenWebpageUseCase::class])
actual class OpenWebpageUseCaseImpl actual constructor() : OpenWebpageUseCase {
    actual override fun invoke(url: String, title: String) { }
}

@Single(binds = [ShareWebpageUseCase::class])
actual class ShareWebpageUseCaseImpl actual constructor() : ShareWebpageUseCase {
    actual override fun invoke(url: String, title: String) { }
}

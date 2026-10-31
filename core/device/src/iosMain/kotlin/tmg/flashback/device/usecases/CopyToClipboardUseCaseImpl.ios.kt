package tmg.flashback.device.usecases

import org.koin.core.annotation.Single
import platform.UIKit.UIPasteboard

@Single(binds = [CopyToClipboardUseCase::class])
actual class CopyToClipboardUseCaseImpl actual constructor() : CopyToClipboardUseCase {
    actual override operator fun invoke(text: String) {
        UIPasteboard.generalPasteboard().setString(text)
    }
}
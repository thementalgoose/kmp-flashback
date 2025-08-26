package tmg.flashback.device.usecases

import platform.UIKit.UIPasteboard

actual class CopyToClipboardUseCaseImpl actual constructor() : CopyToClipboardUseCase {
    actual override operator fun invoke(text: String) {
        UIPasteboard.generalPasteboard().setString(text)
    }
}
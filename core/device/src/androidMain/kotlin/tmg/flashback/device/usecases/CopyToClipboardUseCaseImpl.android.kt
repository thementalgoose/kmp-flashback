package tmg.flashback.device.usecases

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.email_copy_to_clipboard
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.device.manager.UiManager
import kotlin.getValue

actual class CopyToClipboardUseCaseImpl actual constructor() : CopyToClipboardUseCase, KoinComponent {

    private val uiManager: UiManager by inject()
    private val applicationContext: Context by inject()

    actual override operator fun invoke(text: String) {
        copyToClipboard(applicationContext, text)
    }

    private fun copyToClipboard(context: Context, url: String): Boolean {
        val clipboardManager = (context.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager) ?: return false
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", url))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            uiManager.showToUser(string.email_copy_to_clipboard)
        }
        return true
    }
}
package tmg.flashback.device.usecases

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent
import tmg.flashback.device.manager.UiManager
import tmg.flashback.infrastructure.device.Device.string
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.email_copy_to_clipboard


actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase, KoinComponent {

    private val uiManager: UiManager by inject()

    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override fun invoke(
        email: String,
        title: String,
        contents: String
    ) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.flags = FLAG_ACTIVITY_NEW_TASK
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.ifEmpty { "Flashback" })
        emailIntent.putExtra(Intent.EXTRA_TEXT, contents)
        try {
            getApplicationContext().startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            copyToClipboard(getApplicationContext(), email)
        }
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
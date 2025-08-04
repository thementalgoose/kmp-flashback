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
import android.widget.Toast
import org.koin.java.KoinJavaComponent
import tmg.flashback.device.PLAY_STORE_LINK

actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase {

    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override fun invoke() {
        try {
            getApplicationContext().startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK)).apply {
                    flags = FLAG_ACTIVITY_NEW_TASK
                }
            )
        } catch (e: ActivityNotFoundException) {
            copyToClipboard(getApplicationContext(), PLAY_STORE_LINK)
        }
    }


    private fun copyToClipboard(context: Context, url: String): Boolean {
        val clipboardManager = (context.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager) ?: return false
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", url))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show()
        }
        return true
    }
}
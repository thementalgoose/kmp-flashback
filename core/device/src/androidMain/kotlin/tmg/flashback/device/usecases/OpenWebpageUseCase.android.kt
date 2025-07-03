package tmg.flashback.device.usecases

import android.R.string
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import org.koin.java.KoinJavaComponent
import java.net.MalformedURLException

actual class OpenWebpageUseCaseImpl actual constructor(): OpenWebpageUseCase {

    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override fun invoke(url: String) {
        try {
            val intent = webpageIntent(url)
            getApplicationContext().startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            copyToClipboard(getApplicationContext(), url)
        }
    }

    private fun copyToClipboard(context: Context, url: String): Boolean {
        val clipboardManager = (context.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager) ?: return false
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", url))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(context, "Copied", Toast.LENGTH_LONG).show()
        }
        return true
    }

    private fun webpageIntent(url: String): Intent? {
        val uri = try {
            Uri.parse(url)
        } catch (e: MalformedURLException) {
            return null
        }

        val browserSelectorIntent = Intent()
            .setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.parse("https:"))
        val targetIntent = Intent()
            .setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(uri)

        targetIntent.selector = browserSelectorIntent

        return targetIntent
    }
}
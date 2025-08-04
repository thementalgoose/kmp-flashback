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


actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase {
    
    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override fun invoke(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$email")
        emailIntent.flags = FLAG_ACTIVITY_NEW_TASK
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Flashback")
        try {
            getApplicationContext().startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            copyToClipboard(getApplicationContext(), email)
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
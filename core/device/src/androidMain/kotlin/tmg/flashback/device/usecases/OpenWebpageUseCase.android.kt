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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent
import tmg.flashback.device.manager.UiManager
import java.net.MalformedURLException

actual class OpenWebpageUseCaseImpl actual constructor(): OpenWebpageUseCase, KoinComponent {

    private val copyToClipboardUseCase: CopyToClipboardUseCase by inject()

    private val applicationContext: Context by inject()

    actual override fun invoke(url: String, title: String) {
        try {
            val intent = webpageIntent(url)
            applicationContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            copyToClipboardUseCase(url)
        }
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
            .setFlags(FLAG_ACTIVITY_NEW_TASK)
            .setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(uri)

        targetIntent.selector = browserSelectorIntent

        return targetIntent
    }
}
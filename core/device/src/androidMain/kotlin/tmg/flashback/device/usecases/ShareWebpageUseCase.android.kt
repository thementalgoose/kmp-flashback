package tmg.flashback.device.usecases

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class ShareWebpageUseCaseImpl actual constructor() : ShareWebpageUseCase, KoinComponent {

    private val copyToClipboardUseCase: CopyToClipboardUseCase by inject()

    private val applicationContext: Context by inject()

    actual override fun invoke(url: String, title: String) {
        try {
            val intent = webpageIntent(url, title)
            applicationContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            copyToClipboardUseCase(url)
        }
    }

    private fun webpageIntent(url: String, title: String): Intent {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            putExtra(Intent.EXTRA_TITLE, title)
        }, null)
        share.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return share
    }
}
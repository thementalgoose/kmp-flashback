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
import tmg.flashback.device.PLAY_STORE_LINK
import tmg.flashback.device.manager.UiManager

actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase, KoinComponent {

    private val copyToClipboardUseCase: CopyToClipboardUseCase by inject()
    private val applicationContext: Context by inject()

    actual override fun invoke() {
        try {
            applicationContext.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK)).apply {
                    flags = FLAG_ACTIVITY_NEW_TASK
                }
            )
        } catch (e: ActivityNotFoundException) {
            copyToClipboardUseCase(PLAY_STORE_LINK)
        }
    }
}
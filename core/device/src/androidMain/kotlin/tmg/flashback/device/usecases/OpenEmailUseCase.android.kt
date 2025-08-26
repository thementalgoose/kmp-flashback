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
import androidx.core.app.ShareCompat
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent
import tmg.flashback.device.manager.UiManager
import tmg.flashback.infrastructure.device.Device.string
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.email_copy_to_clipboard


actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase, KoinComponent {

    private val copyToClipboardUseCase: CopyToClipboardUseCase by inject()
    private val applicationContext: Context by inject()

    actual override fun invoke(
        email: String,
        title: String,
        contents: String
    ) {
        try {
            val intent = ShareCompat.IntentBuilder(applicationContext)
                .setEmailTo(arrayOf(email))
                .setSubject(title.ifEmpty { "Flashback" })
                .setText(contents)
                .setChooserTitle("Email")
                .createChooserIntent()
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            copyToClipboardUseCase(email)
        }
    }
}
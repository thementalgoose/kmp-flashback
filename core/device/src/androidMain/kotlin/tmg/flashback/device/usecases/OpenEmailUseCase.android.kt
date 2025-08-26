package tmg.flashback.device.usecases

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.text.Html
import androidx.core.app.ShareCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase, KoinComponent {

    private val copyToClipboardUseCase: CopyToClipboardUseCase by inject()
    private val applicationContext: Context by inject()

    actual override fun invoke(
        email: String,
        title: String,
        contents: String
    ) {
        try {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.setDataAndType(Uri.parse("mailto:"), "text/html")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email)) // Multiple recipients
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.ifEmpty { "Flashback" })
            emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(contents, FROM_HTML_MODE_LEGACY))
            emailIntent.flags = FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            copyToClipboardUseCase(email)
        }
    }
}
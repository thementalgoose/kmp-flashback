package tmg.flashback.device.usecases

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.java.KoinJavaComponent


actual class OpenEmailUseCaseImpl actual constructor(): OpenEmailUseCase {
    
    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override fun invoke(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$email")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Flashback")
        getApplicationContext().startActivity(emailIntent)
    }
}
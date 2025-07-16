package tmg.flashback.device.usecases

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
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
        } catch (e: ActivityNotFoundException) { }
    }
}
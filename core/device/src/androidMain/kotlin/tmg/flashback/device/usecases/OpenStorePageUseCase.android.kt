package tmg.flashback.device.usecases

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.net.toUri
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.device.repositories.DeviceRepository

actual class OpenStorePageUseCaseImpl actual constructor(): OpenStorePageUseCase, KoinComponent {

    private val copyToClipboardUseCase: CopyToClipboardUseCase by inject()
    private val applicationContext: Context by inject()
    private val deviceRepository: DeviceRepository by inject()

    actual override fun invoke() {
        try {
            applicationContext.startActivity(
                Intent(Intent.ACTION_VIEW, deviceRepository.playStoreLink.toUri()).apply {
                    flags = FLAG_ACTIVITY_NEW_TASK
                }
            )
        } catch (e: ActivityNotFoundException) {
            copyToClipboardUseCase(deviceRepository.playStoreLink)
        }
    }
}
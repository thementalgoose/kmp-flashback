package tmg.flashback

import android.app.Application
import android.app.NotificationManager
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.di.doInitKoin

class FlashbackApplication: Application(), KoinComponent {

    val configManager: ConfigManager by inject()

    override fun onCreate() {
        super.onCreate()

        doInitKoin {
            androidContext(this@FlashbackApplication)
            androidLogger()
        }

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground,
                showPushNotification = true,
                notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData()
            )
        )
        configManager.initialiseRemoteConfig(
            defaultValues = RemoteConfigDefaults.defaults
        )
    }
}
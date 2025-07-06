package tmg.flashback

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import tmg.flashback.di.doInitKoin
import tmg.flashback.notifications.repositories.NotificationRepository

class FlashbackApplication: Application(), KoinComponent {

    private val flashbackAndroidStartup by inject<FlashbackAndroidStartup>()

    override fun onCreate() {
        super.onCreate()

        doInitKoin {
            androidContext(this@FlashbackApplication)
            androidLogger()
        }

        flashbackAndroidStartup.startup(application = this)
    }
}
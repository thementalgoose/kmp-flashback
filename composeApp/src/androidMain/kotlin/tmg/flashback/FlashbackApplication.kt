package tmg.flashback

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.di.initKoin

class FlashbackApplication: Application(), KoinComponent {

    val configManager: ConfigManager by inject()

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@FlashbackApplication)
            androidLogger()
        }

        configManager.initialiseRemoteConfig(
            defaultValues = mapOf(
                "config_url" to "https://flashback.pages.dev"
            )
        )
    }
}
package tmg.flashback

import android.app.Application
import multiplatform.network.cmptoast.AppContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.composeApp.FlashbackAndroidStartup
import tmg.flashback.composeApp.di.doInitKoin

class FlashbackApplication: Application(), KoinComponent {

    private val flashbackAndroidStartup by inject<FlashbackAndroidStartup>()

    override fun onCreate() {
        super.onCreate()

        doInitKoin {
            androidContext(this@FlashbackApplication)
            androidLogger()
        }

        AppContext.apply { set(this@FlashbackApplication) }

        flashbackAndroidStartup.startup(application = this)
    }
}
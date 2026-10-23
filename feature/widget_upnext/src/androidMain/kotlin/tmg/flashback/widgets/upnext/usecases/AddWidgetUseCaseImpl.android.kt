package tmg.flashback.widgets.upnext.usecases

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.widgets.upnext.presentation.UpNextWidgetReceiver

actual class AddWidgetUseCaseImpl actual constructor() : AddWidgetUseCase, KoinComponent {

    private val applicationContext: Context by inject()

    private val appWidgetManager: AppWidgetManager
        get() = AppWidgetManager.getInstance(applicationContext)

    actual override val isSupported: Boolean
        get() = appWidgetManager.isRequestPinAppWidgetSupported

    actual override operator fun invoke(): Boolean {
        if (!isSupported) {
            return false
        }
        val provider = ComponentName(applicationContext, UpNextWidgetReceiver::class.java)
        return appWidgetManager.requestPinAppWidget(provider, null, null)
    }
}

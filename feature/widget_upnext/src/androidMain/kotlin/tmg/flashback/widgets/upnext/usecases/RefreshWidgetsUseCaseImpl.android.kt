package tmg.flashback.widgets.upnext.usecases

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.widgets.upnext.presentation.UpNextWidgetReceiver

actual class RefreshWidgetsUseCaseImpl actual constructor() : RefreshWidgetsUseCase, KoinComponent {

    private val applicationContext: Context by inject()

    actual override operator fun invoke() {
        val zClass = UpNextWidgetReceiver::class.java

        val manager = AppWidgetManager.getInstance(applicationContext)
        val ids = manager.getAppWidgetIds(ComponentName(applicationContext, zClass))

        val intent = Intent(applicationContext, zClass)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)

        applicationContext.sendBroadcast(intent)
    }
}
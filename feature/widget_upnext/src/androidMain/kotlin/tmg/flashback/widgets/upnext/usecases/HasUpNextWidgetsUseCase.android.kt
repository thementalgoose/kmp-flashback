package tmg.flashback.widgets.upnext.usecases

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import tmg.flashback.widgets.upnext.presentation.UpNextWidgetReceiver

internal class HasUpNextWidgetsUseCaseImpl(
    private val applicationContext: Context
): HasUpNextWidgetsUseCase {

    private val appWidgetManager: AppWidgetManager
        get() = AppWidgetManager.getInstance(applicationContext)

    override fun invoke(): Boolean {
        val currentIds = appWidgetManager.getAppWidgetIds(ComponentName(applicationContext, UpNextWidgetReceiver::class.java))
        return currentIds.isNotEmpty()
    }
}
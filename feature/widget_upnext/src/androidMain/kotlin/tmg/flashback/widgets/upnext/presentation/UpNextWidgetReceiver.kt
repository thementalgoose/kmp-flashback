package tmg.flashback.widgets.upnext.presentation

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class UpNextWidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = UpNextWidget()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.i("UpNextWidget", "onReceive ${intent.extras?.getIntArray(EXTRA_APPWIDGET_IDS)?.joinToString { it.toString() }}")
        runBlocking(Dispatchers.IO) {
            glanceAppWidget.updateAll(context)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i("UpNextWidget", "onUpdate ${appWidgetIds.joinToString { it.toString() }}")
    }
}




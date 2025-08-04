package tmg.flashback.widgets.upnext.presentation

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.widgets.upnext.presentation.style.utils.appWidgetId
import java.util.UUID

internal class UpNextWidgetRefreshWidget: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Log.i("UpNextWidget", "Refresh widget action for $glanceId (${glanceId.appWidgetId})")
        val intent = Intent(context, UpNextWidgetReceiver::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(glanceId.appWidgetId))
        context.sendBroadcast(intent)

        updateAppWidgetState(context, glanceId) {
            it[stringPreferencesKey("uuid")] = UUID.randomUUID().toString()
        }
        UpNextWidget().update(context, glanceId)
    }
}
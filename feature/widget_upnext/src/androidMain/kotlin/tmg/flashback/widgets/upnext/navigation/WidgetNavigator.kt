package tmg.flashback.widgets.upnext.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent

interface WidgetNavigator {
    fun getHomeActivity(): Class<out Activity>
    fun getHomeIntent(context: Context): Intent
}
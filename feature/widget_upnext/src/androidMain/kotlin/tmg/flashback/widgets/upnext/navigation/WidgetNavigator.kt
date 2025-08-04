package tmg.flashback.widgets.upnext.navigation

import android.app.Activity

interface WidgetNavigator {
    fun getHomeActivity(): Class<out Activity>
}
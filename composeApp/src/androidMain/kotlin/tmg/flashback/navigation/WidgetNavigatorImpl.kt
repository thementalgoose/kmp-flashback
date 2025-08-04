package tmg.flashback.navigation

import android.app.Activity
import tmg.flashback.MainActivity
import tmg.flashback.widgets.upnext.navigation.WidgetNavigator

class WidgetNavigatorImpl(): WidgetNavigator {
    override fun <T : Activity> getHomeActivity(): Class<*> {
        return MainActivity::class.java
    }
}
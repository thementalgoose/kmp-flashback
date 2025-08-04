package tmg.flashback.navigation

import android.app.Activity
import tmg.flashback.MainActivity
import tmg.flashback.widgets.upnext.navigation.WidgetNavigator

class WidgetNavigatorImpl(): WidgetNavigator {
    override fun getHomeActivity(): Class<out Activity> {
        return MainActivity::class.java
    }
}
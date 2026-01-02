package tmg.flashback.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import tmg.flashback.MainActivity
import tmg.flashback.widgets.upnext.navigation.WidgetNavigator

class WidgetNavigatorImpl(): WidgetNavigator {
    override fun getHomeActivity(): Class<out Activity> {
        return MainActivity::class.java
    }

    override fun getHomeIntent(context: Context): Intent {
        return Intent(context, Class.forName("tmg.flashback.MainActivity")).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
    }
}
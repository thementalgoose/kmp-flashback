package tmg.flashback.ui.activity

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference

class ActivityProvider: Application.ActivityLifecycleCallbacks {

    private var _activity: WeakReference<Activity>? = null
    private var startCount = 0

    val isForeground: Boolean
        get() = startCount != 0

    val activity: Activity?
        @Synchronized
        get() = _activity?.get()

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        update(activity, "CREATED")
    }

    override fun onActivityStarted(activity: Activity) {
        update(activity, "CREATED")
        startCount++
    }

    override fun onActivityResumed(activity: Activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            update(activity, "RESUMED")
        }
    }

    fun onWindowFocusObtained(activity: Activity) {
        update(activity, "WINDOW_FOCUS_OBTAINED")
    }

    override fun onActivityPaused(activity: Activity) { }
    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) { }
    override fun onActivityDestroyed(activity: Activity) { }

    override fun onActivityStopped(activity: Activity) {
        startCount--
    }

    private fun update(activity: Activity, lifecycleState: String) {
        this._activity = WeakReference(activity)
        Log.i("Flashback", "Activity updated to ${activity.javaClass.simpleName} - $lifecycleState")
    }
}
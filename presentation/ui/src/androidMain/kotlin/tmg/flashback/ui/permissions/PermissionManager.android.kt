package tmg.flashback.ui.permissions

import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.core.app.AlarmManagerCompat
import kotlinx.coroutines.CompletableDeferred
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.ui.activity.ActivityProvider
import tmg.flashback.ui.activity.BaseActivity

actual class PermissionManagerImpl actual constructor(): PermissionManager, KoinComponent {

    private var completableDeferred: CompletableDeferred<PermissionState>? = null
    private val activityProvider: ActivityProvider by inject()

    val activityContract = ActivityResultCallback<Boolean> { isGranted ->
        completableDeferred?.complete(when (isGranted) {
            true -> PermissionState.Granted
            false -> PermissionState.NotGranted
        })
        completableDeferred = null
    }

    actual override suspend fun requestPermission(permission: Permission): CompletableDeferred<PermissionState> {
        completableDeferred = CompletableDeferred()
        val activity = activityProvider.activity ?: return CompletableDeferred(PermissionState.NotDetermined)
        (activity as? BaseActivity)?.requestPermission(permission)
        return completableDeferred ?: CompletableDeferred(PermissionState.NotDetermined)
    }

    actual override suspend fun getPermissionState(permission: Permission): PermissionState {
        val result = when (activityProvider.activity?.checkSelfPermission(permission.manifestString)) {
            PERMISSION_GRANTED -> PermissionState.Granted
            PERMISSION_DENIED -> PermissionState.NotGranted
            else -> PermissionState.NotDetermined
        }
        Log.d("Notifications", "getPermissionState $result")
        return result
    }
}
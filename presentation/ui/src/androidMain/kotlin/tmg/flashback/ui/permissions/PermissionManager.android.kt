package tmg.flashback.ui.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CompletableDeferred
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.infrastructure.device.Device
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

    actual override fun openAppSettings() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        settingsIntent.putExtra("app_package", Device.applicationId)
        settingsIntent.putExtra(Settings.EXTRA_APP_PACKAGE, Device.applicationId)
        activityProvider.activity?.startActivity(settingsIntent)
    }

    actual override fun openNotificationSettings() {
        val settingsIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        settingsIntent.putExtra("app_package", Device.applicationId)
        settingsIntent.putExtra(Settings.EXTRA_APP_PACKAGE, Device.applicationId)
        activityProvider.activity?.startActivity(settingsIntent)
    }
}
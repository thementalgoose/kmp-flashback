package tmg.flashback.ui.activity

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import org.koin.java.KoinJavaComponent
import org.koin.android.ext.android.inject
import tmg.flashback.ui.permissions.Permission
import tmg.flashback.ui.permissions.PermissionManager
import tmg.flashback.ui.permissions.PermissionManagerImpl
import tmg.flashback.ui.permissions.manifestString
import kotlin.getValue

open class BaseActivity: ComponentActivity() {

    private val activityProvider: ActivityProvider by inject()

    private val _permissionManager: PermissionManager by inject()
    private val permissionManager by lazy {
        _permissionManager as PermissionManagerImpl
    }

    val launcher = registerForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        callback = permissionManager.activityContract
    )

    fun requestPermission(permission: Permission) {
        launcher.launch(permission.manifestString)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            activityProvider.onWindowFocusObtained(this)
        }
    }
}
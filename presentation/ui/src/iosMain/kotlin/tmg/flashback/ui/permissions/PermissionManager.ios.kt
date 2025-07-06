package tmg.flashback.ui.permissions

import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.PermissionsController

actual class PermissionManager actual constructor() {

    lateinit var permissionsController: PermissionsController

    actual suspend fun providePermission(permission: Permission): PermissionState {
        try {
            permissionsController.providePermission(permission.toMoko())
            return getPermissionState(permission)
        } catch (e: DeniedException) {
            return PermissionState.Denied
        } catch (e: DeniedAlwaysException) {
            return PermissionState.DeniedAlways
        }
    }

    actual suspend fun isPermissionGranted(permission: Permission): Boolean {
        return permissionsController.isPermissionGranted(permission.toMoko())
    }

    actual suspend fun getPermissionState(permission: Permission): PermissionState {
        return permissionsController.getPermissionState(permission.toMoko()).fromMoko()
    }

    actual fun openAppSettings() {
        permissionsController.openAppSettings()
    }
}
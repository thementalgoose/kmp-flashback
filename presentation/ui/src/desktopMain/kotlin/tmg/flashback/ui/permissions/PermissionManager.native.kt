package tmg.flashback.ui.permissions

actual class PermissionManager actual constructor() {

    actual suspend fun providePermission(permission: Permission): PermissionState {
        return getPermissionState(permission)
    }

    actual suspend fun isPermissionGranted(permission: Permission): Boolean {
        return false
    }

    actual suspend fun getPermissionState(permission: Permission): PermissionState {
        return PermissionState.NotDetermined
    }

    actual fun openAppSettings() {
        // n/a
    }
}
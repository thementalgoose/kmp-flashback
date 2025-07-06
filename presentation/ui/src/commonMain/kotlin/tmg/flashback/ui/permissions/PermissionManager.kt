package tmg.flashback.ui.permissions

/**
 * Facade around moko.PermissionsController because it doesn't contain
 *  a desktop target and has to be implemented in android and ios only.
 *
 * Functionality stubbed out in other targets
 */
expect class PermissionManager() {

    suspend fun providePermission(permission: Permission): PermissionState

    suspend fun isPermissionGranted(permission: Permission): Boolean

    suspend fun getPermissionState(permission: Permission): PermissionState

    fun openAppSettings()
}
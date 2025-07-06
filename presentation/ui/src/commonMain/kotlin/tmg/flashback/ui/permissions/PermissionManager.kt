package tmg.flashback.ui.permissions

import kotlinx.coroutines.CompletableDeferred

/**
 * Facade around moko.PermissionsController because it doesn't contain
 *  a desktop target and has to be implemented in android and ios only.
 *
 * Functionality stubbed out in other targets
 */
interface PermissionManager {

    suspend fun requestPermission(permission: Permission): CompletableDeferred<PermissionState>

    suspend fun getPermissionState(permission: Permission): PermissionState

    fun openAppSettings()

    fun openNotificationSettings()
}

internal expect class PermissionManagerImpl(): PermissionManager {

    override suspend fun requestPermission(permission: Permission): CompletableDeferred<PermissionState>

    override suspend fun getPermissionState(permission: Permission): PermissionState

    override fun openAppSettings()

    override fun openNotificationSettings()
}
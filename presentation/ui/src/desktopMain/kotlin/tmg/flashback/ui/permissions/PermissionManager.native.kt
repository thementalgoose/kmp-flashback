package tmg.flashback.ui.permissions

import kotlinx.coroutines.CompletableDeferred
import org.koin.core.annotation.Single

@Single(binds = [PermissionManager::class])
actual class PermissionManagerImpl actual constructor(): PermissionManager {

    actual override suspend fun requestPermission(permission: Permission): CompletableDeferred<PermissionState> {
        return CompletableDeferred(PermissionState.NotDetermined)
    }

    actual override suspend fun getPermissionState(permission: Permission): PermissionState {
        return PermissionState.NotDetermined
    }
}
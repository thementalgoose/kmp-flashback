package tmg.flashback.device.usecases

import tmg.flashback.device.models.Permission
import tmg.flashback.device.models.PermissionState

actual class IsPermissionGrantedUseCaseImpl actual constructor(): IsPermissionGrantedUseCase {
    actual override suspend fun invoke(permission: Permission): PermissionState {

        return PermissionState.Denied
    }
}
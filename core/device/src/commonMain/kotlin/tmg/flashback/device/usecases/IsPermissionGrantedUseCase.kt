package tmg.flashback.device.usecases

import tmg.flashback.device.models.Permission
import tmg.flashback.device.models.PermissionState

interface IsPermissionGrantedUseCase {
    suspend operator fun invoke(permission: Permission): PermissionState
}

expect class IsPermissionGrantedUseCaseImpl(): IsPermissionGrantedUseCase {
    override suspend fun invoke(permission: Permission): PermissionState
}